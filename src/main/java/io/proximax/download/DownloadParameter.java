package io.proximax.download;

import io.proximax.privacy.strategy.PrivacyStrategy;

public class DownloadParameter {

    String transactionHash;
    String rootDataHash;
    PrivacyStrategy privacyStrategy;
    String digest;

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getRootDataHash() {
        return rootDataHash;
    }

    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    public String getDigest() {
        return digest;
    }

    public static DownloadParameterBuilder createWithTransactionHash(String transactionHash) {
        return new DownloadParameterBuilder(transactionHash);
    }

    public static DownloadParameterBuilder createWithRootDataHash(String rootDataHash, String digest) {
        return new DownloadParameterBuilder(rootDataHash, digest);
    }
}
