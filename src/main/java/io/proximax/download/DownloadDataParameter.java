package io.proximax.download;

import io.proximax.model.StoreType;
import io.proximax.privacy.strategy.PrivacyStrategy;

public class DownloadDataParameter {

    private final String dataHash;
    private final PrivacyStrategy privacyStrategy;
    private final String digest;
    private final StoreType storeType;

    DownloadDataParameter(String dataHash, PrivacyStrategy privacyStrategy, String digest, StoreType storeType) {
        this.dataHash = dataHash;
        this.privacyStrategy = privacyStrategy;
        this.digest = digest;
        this.storeType = storeType;
    }

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
