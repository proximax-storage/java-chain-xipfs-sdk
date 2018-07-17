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

    private DownloadParameter param;

    DownloadParameterBuilder(String transactionHash) {
        checkParameter(transactionHash != null, "transactionHash is required");

        param = new DownloadParameter();
        param.transactionHash = transactionHash;
    }

    DownloadParameterBuilder(String rootDataHash, String digest) {
        checkParameter(rootDataHash != null, "rootDataHash is required");

        param = new DownloadParameter();
        param.rootDataHash = rootDataHash;
        param.digest = digest;
    }

    public DownloadParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        param.privacyStrategy = privacyStrategy;
        return this;
    }

    public DownloadParameterBuilder plainPrivacy() {
        param.privacyStrategy = PlainPrivacyStrategy.create(null);
        return this;
    }

    public DownloadParameterBuilder securedWithNemKeysPrivacyStrategy(String privateKey, String publicKey) {
        param.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey, null);
        return this;
    }

    public DownloadParameterBuilder securedWithPasswordPrivacyStrategy(String password) {
        param.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, null);
        return this;
    }

    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart... secretParts) {
        param.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                null, secretParts);
        return this;
    }

    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      List<SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart> secretParts) {
        param.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      Map<Integer, byte[]> secretParts) {
        param.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadParameter build() {
        if (param.privacyStrategy == null)
            param.privacyStrategy = PlainPrivacyStrategy.create(null);
        return param;
    }

}
