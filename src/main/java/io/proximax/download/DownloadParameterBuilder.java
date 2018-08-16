package io.proximax.download;

import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;

import java.util.List;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class DownloadParameterBuilder {

    private String transactionHash;
    private String rootDataHash;
    private PrivacyStrategy privacyStrategy;
    private String digest;

    DownloadParameterBuilder(String transactionHash) {
        checkParameter(transactionHash != null, "transactionHash is required");

        this.transactionHash = transactionHash;
    }

    DownloadParameterBuilder(String rootDataHash, String digest) {
        checkParameter(rootDataHash != null, "rootDataHash is required");

        this.rootDataHash = rootDataHash;
        this.digest = digest;
    }

    public DownloadParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    public DownloadParameterBuilder plainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create(null);
        return this;
    }

    public DownloadParameterBuilder securedWithNemKeysPrivacyStrategy(String privateKey, String publicKey) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey, null);
        return this;
    }

    public DownloadParameterBuilder securedWithPasswordPrivacyStrategy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, null);
        return this;
    }

    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                null, secretParts);
        return this;
    }

    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      List<SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadParameter build() {
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create(null);
        return new DownloadParameter(transactionHash, rootDataHash, privacyStrategy, digest);
    }

}
