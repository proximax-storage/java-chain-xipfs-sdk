package io.proximax.download;

import io.proximax.privacy.strategy.PrivacyStrategy;

public class DownloadDataParameter {

    private final String dataHash;
    private final PrivacyStrategy privacyStrategy;
    private final String digest;

    DownloadDataParameter(String dataHash, PrivacyStrategy privacyStrategy, String digest) {
        this.dataHash = dataHash;
        this.privacyStrategy = privacyStrategy;
        this.digest = digest;
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

    public static DownloadDataParameterBuilder create(String dataHash) {
        return new DownloadDataParameterBuilder(dataHash);
    }
}
