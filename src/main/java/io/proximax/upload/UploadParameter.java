package io.proximax.upload;

import io.proximax.model.StoreType;
import io.proximax.privacy.strategy.PrivacyStrategy;

import java.util.List;

import static io.proximax.model.Version.VERSION;
import static java.util.Collections.unmodifiableList;

public class UploadParameter {

    private final String signerPrivateKey;
    private final String recipientPublicKey;
    private final String description;
    private final PrivacyStrategy privacyStrategy;
    private final Boolean computeDigest;
    private final StoreType storeType;
    private final String version;
    private final List<UploadParameterData> dataList;

    UploadParameter(String signerPrivateKey, String recipientPublicKey, String description, PrivacyStrategy privacyStrategy,
                           Boolean computeDigest, StoreType storeType, List<UploadParameterData> dataList) {
        this.signerPrivateKey = signerPrivateKey;
        this.recipientPublicKey = recipientPublicKey;
        this.description = description;
        this.privacyStrategy = privacyStrategy;
        this.computeDigest = computeDigest;
        this.storeType = storeType;
        this.dataList = dataList;
        this.version = VERSION;
    }

    public String getSignerPrivateKey() {
        return signerPrivateKey;
    }

    public String getRecipientPublicKey() {
        return recipientPublicKey;
    }

    public String getDescription() {
        return description;
    }

    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    public Boolean getComputeDigest() {
        return computeDigest;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public String getVersion() {
        return version;
    }

    public List<UploadParameterData> getDataList() {
        return unmodifiableList(dataList);
    }

    public static UploadParameterBuilder create(String signerPrivateKey, String recipientPublicKey) {
        return new UploadParameterBuilder(signerPrivateKey, recipientPublicKey);
    }
}
