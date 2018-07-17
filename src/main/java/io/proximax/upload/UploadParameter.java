package io.proximax.upload;

import io.proximax.model.RawDataModel;
import io.proximax.model.StoreType;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;

import java.util.ArrayList;
import java.util.List;

import static io.proximax.model.Version.VERSION;
import static java.util.Collections.unmodifiableList;

public class UploadParameter {

    String signerPrivateKey;
    String recipientPublicKey;
    String description;
    PrivacyStrategy privacyStrategy;
    Boolean computeDigest;
    StoreType storeType;
    final String version;
    List<UploadParameterData> dataList;

    UploadParameter(String signerPrivateKey, String recipientPublicKey) {
        this.dataList = new ArrayList<>();
        this.signerPrivateKey = signerPrivateKey;
        this.recipientPublicKey = recipientPublicKey;
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
