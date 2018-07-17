package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.model.StoreType;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.JsonUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;

import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class CreateProximaxMessagePayloadService {

    private final IpfsUploadService ipfsUploadService;
    private final DigestUtils digestUtils;
    private final PrivacyDataEncryptionUtils privacyDataEncryptionUtils;

    public CreateProximaxMessagePayloadService(IpfsConnection ipfsConnection) {
        this.ipfsUploadService = new IpfsUploadService(ipfsConnection);
        this.digestUtils = new DigestUtils();
        this.privacyDataEncryptionUtils = new PrivacyDataEncryptionUtils();
    }

    CreateProximaxMessagePayloadService(IpfsUploadService ipfsUploadService, DigestUtils digestUtils,
                                        PrivacyDataEncryptionUtils privacyDataEncryptionUtils) {
        this.ipfsUploadService = ipfsUploadService;
        this.digestUtils = digestUtils;
        this.privacyDataEncryptionUtils = privacyDataEncryptionUtils;
    }

    public Observable<ProximaxMessagePayloadModel> createMessagePayload(UploadParameter uploadParameter, ProximaxRootDataModel rootData) {
        checkParameter(uploadParameter != null, "uploadParameter is required");
        checkParameter(rootData != null, "rootData is required");

        final Observable<byte[]> encryptedRootDataOb = encryptRootData(uploadParameter.getPrivacyStrategy(), rootData).cache();
        final Observable<Optional<String>> rootDigestOb = encryptedRootDataOb.flatMap(
                encryptedRootData -> computeDigestOfRootData(uploadParameter.getComputeDigest(), encryptedRootData));
        final Observable<String> rootDataHashOb = encryptedRootDataOb.flatMap(this::ipfsUploadRootData);

        return Observable.zip(rootDataHashOb, rootDigestOb,
                (rootDataHash, rootDigest) -> createMessagePayload(uploadParameter, rootDataHash, rootDigest));
    }

    private Observable<byte[]> encryptRootData(PrivacyStrategy privacyStrategy, ProximaxRootDataModel rootData) {
        return privacyDataEncryptionUtils.encrypt(privacyStrategy, JsonUtils.toJson(rootData).getBytes());
    }

    private Observable<Optional<String>> computeDigestOfRootData(boolean computeDigest, byte[] encryptedRootData) {
        return computeDigest ? digestUtils.digest(encryptedRootData).map(Optional::of) : Observable.just(Optional.empty());
    }

    private Observable<String> ipfsUploadRootData(byte[] encryptedRootData) {
        return ipfsUploadService.upload(encryptedRootData, StoreType.RESOURCE).map(IpfsUploadResponse::getDataHash);
    }

    private ProximaxMessagePayloadModel createMessagePayload(UploadParameter uploadParameter, String rootDataHash, Optional<String> rootDigest) {
        return ProximaxMessagePayloadModel.create(rootDataHash, rootDigest.orElse(null), uploadParameter.getDescription(),
                uploadParameter.getPrivacyStrategy().getPrivacyType(), uploadParameter.getPrivacyStrategy().getPrivacySearchTag(),
                uploadParameter.getVersion());
    }

}
