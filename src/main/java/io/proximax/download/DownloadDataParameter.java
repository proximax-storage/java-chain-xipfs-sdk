package io.proximax.download;

import io.proximax.model.StoreType;
import io.proximax.privacy.strategy.PrivacyStrategy;

public class DownloadDataParameter {

    String dataHash;
    PrivacyStrategy privacyStrategy;
    String digest;
    StoreType storeType;

    public String getDataHash() {
        return dataHash;
    }

    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    public String getDigest() {
        return digest;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public static DownloadDataParameterBuilder create(String dataHash) {
        return new DownloadDataParameterBuilder(dataHash);
    }
}
