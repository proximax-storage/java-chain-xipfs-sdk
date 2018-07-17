package io.proximax.download;

import io.proximax.model.StoreType;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;

import java.util.List;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class DownloadDataParameterBuilder {

    private DownloadDataParameter param;

    DownloadDataParameterBuilder(String dataHash) {
        checkParameter(dataHash != null, "dataHash is required");

        param = new DownloadDataParameter();
        param.dataHash = dataHash;
    }

    public DownloadDataParameterBuilder digest(String digest) {
        param.digest = digest;
        return this;
    }

    public DownloadDataParameterBuilder storeType(StoreType storeType) {
        param.storeType = storeType;
        return this;
    }

    public DownloadDataParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        param.privacyStrategy = privacyStrategy;
        return this;
    }

    public DownloadDataParameterBuilder plainPrivacy() {
        param.privacyStrategy = PlainPrivacyStrategy.create(null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithNemKeysPrivacyStrategy(String privateKey, String publicKey) {
        param.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey, null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithPasswordPrivacyStrategy(String password) {
        param.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart... secretParts) {
        param.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                null, secretParts);
        return this;
    }

    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                List<SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart> secretParts) {
        param.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                Map<Integer, byte[]> secretParts) {
        param.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadDataParameter build() {
        if (param.privacyStrategy == null)
            param.privacyStrategy = PlainPrivacyStrategy.create(null);
        if (param.storeType == null)
            param.storeType = StoreType.RESOURCE;
        return param;
    }

}
