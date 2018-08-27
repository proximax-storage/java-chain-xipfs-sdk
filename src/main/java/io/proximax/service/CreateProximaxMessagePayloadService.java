package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.JsonUtils;
import io.reactivex.Observable;

import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for creating a message payload
 */
public class CreateProximaxMessagePayloadService {

    private final IpfsUploadService ipfsUploadService;
    private final DigestUtils digestUtils;

    /**
     * Construct service class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public CreateProximaxMessagePayloadService(IpfsConnection ipfsConnection) {
        this.ipfsUploadService = new IpfsUploadService(ipfsConnection);
        this.digestUtils = new DigestUtils();
    }

    CreateProximaxMessagePayloadService(IpfsUploadService ipfsUploadService, DigestUtils digestUtils) {
        this.ipfsUploadService = ipfsUploadService;
        this.digestUtils = digestUtils;
    }

    /**
     * Creates a message payload
     * @param uploadParameter the upload parameter
     * @param rootData a created root data
     * @return the created message payload
     */
    public Observable<ProximaxMessagePayloadModel> createMessagePayload(UploadParameter uploadParameter, ProximaxRootDataModel rootData) {
        checkParameter(uploadParameter != null, "uploadParameter is required");
        checkParameter(rootData != null, "rootData is required");

        final byte[] encryptedRootData = encryptRootData(uploadParameter.getPrivacyStrategy(), rootData);
        final Observable<Optional<String>> rootDigestOb = computeDigestOfRootData(uploadParameter.getComputeDigest(), encryptedRootData);
        final Observable<String> rootDataHashOb = ipfsUploadRootData(encryptedRootData);

        return Observable.zip(rootDataHashOb, rootDigestOb,
                (rootDataHash, rootDigest) -> createMessagePayload(uploadParameter, rootDataHash, rootDigest));
    }

    private byte[] encryptRootData(PrivacyStrategy privacyStrategy, ProximaxRootDataModel rootData) {
        return privacyStrategy.encryptData(JsonUtils.toJson(rootData).getBytes());
    }

    private Observable<Optional<String>> computeDigestOfRootData(boolean computeDigest, byte[] encryptedRootData) {
        return computeDigest ? digestUtils.digest(encryptedRootData).map(Optional::of) : Observable.just(Optional.empty());
    }

    private Observable<String> ipfsUploadRootData(byte[] encryptedRootData) {
        return ipfsUploadService.uploadByteArray(encryptedRootData).map(IpfsUploadResponse::getDataHash);
    }

    private ProximaxMessagePayloadModel createMessagePayload(UploadParameter uploadParameter, String rootDataHash, Optional<String> rootDigest) {
        return ProximaxMessagePayloadModel.create(rootDataHash, rootDigest.orElse(null), uploadParameter.getDescription(),
                uploadParameter.getPrivacyStrategy().getPrivacyType(), uploadParameter.getPrivacyStrategy().getPrivacySearchTag(),
                uploadParameter.getVersion());
    }

}
