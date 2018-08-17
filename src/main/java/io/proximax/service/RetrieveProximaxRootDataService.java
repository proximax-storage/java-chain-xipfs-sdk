package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.JsonUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;

import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving root data
 */
public class RetrieveProximaxRootDataService {
    private final IpfsDownloadService ipfsDownloadService;
    private final DigestUtils digestUtils;
    private final PrivacyDataEncryptionUtils privacyDataEncryptionUtils;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public RetrieveProximaxRootDataService(IpfsConnection ipfsConnection) {
        this.ipfsDownloadService = new IpfsDownloadService(ipfsConnection);
        this.digestUtils = new DigestUtils();
        this.privacyDataEncryptionUtils = new PrivacyDataEncryptionUtils();
    }

    RetrieveProximaxRootDataService(IpfsDownloadService ipfsDownloadService, DigestUtils digestUtils,
                                    PrivacyDataEncryptionUtils privacyDataEncryptionUtils) {
        this.ipfsDownloadService = ipfsDownloadService;
        this.digestUtils = digestUtils;
        this.privacyDataEncryptionUtils = privacyDataEncryptionUtils;
    }

    /**
     * Retrieves the root data
     * @param downloadParam the download parameter
     * @param messagePayloadOpt the optional message payload
     * @return the root data
     */
    public Observable<ProximaxRootDataModel> getRootData(DownloadParameter downloadParam, Optional<ProximaxMessagePayloadModel> messagePayloadOpt) {
        checkParameter(downloadParam != null, "downloadParam is required");
        checkParameter(messagePayloadOpt != null, "messagePayloadOpt is required");

        final String rootDataHash = getRootDataHash(downloadParam.getRootDataHash(), messagePayloadOpt);
        final String rootDigest = getRootDigest(downloadParam.getDigest(), messagePayloadOpt);

        final Observable<byte[]> undecryptedRootDataOb = downloadRootData(rootDataHash);
        final Observable<byte[]> undecryptedAndVerifiedRootDataOb = verifyDataWithRootDigest(rootDigest, undecryptedRootDataOb);

        return decryptRootData(downloadParam.getPrivacyStrategy(), undecryptedAndVerifiedRootDataOb);
    }

    private String getRootDataHash(String rootDataHash, Optional<ProximaxMessagePayloadModel> messagePayloadOpt) {
        return messagePayloadOpt.map(ProximaxMessagePayloadModel::getRootDataHash).orElse(rootDataHash);
    }

    private String getRootDigest(String rootDigest, Optional<ProximaxMessagePayloadModel> messagePayloadOpt) {
        return messagePayloadOpt.map(ProximaxMessagePayloadModel::getDigest).orElse(rootDigest);
    }

    private Observable<byte[]> downloadRootData(String rootDataHash) {
        return ipfsDownloadService.download(rootDataHash);
    }

    private Observable<byte[]> verifyDataWithRootDigest(String rootDigest, Observable<byte[]> undecryptedRootDataOb) {
        return undecryptedRootDataOb.flatMap(undecryptedRootData ->
            digestUtils.validateDigest(undecryptedRootData, rootDigest).map(result -> undecryptedRootData));
    }

    private Observable<ProximaxRootDataModel> decryptRootData(PrivacyStrategy privacyStrategy,
                                                              Observable<byte[]> undecryptedAndVerifiedRootDataOb) {
        return undecryptedAndVerifiedRootDataOb.flatMap(undecryptedRootData ->
                privacyDataEncryptionUtils.decrypt(privacyStrategy, undecryptedRootData))
                .map(String::new)
                .map(json -> JsonUtils.fromJson(json, ProximaxRootDataModel.class));
    }
}
