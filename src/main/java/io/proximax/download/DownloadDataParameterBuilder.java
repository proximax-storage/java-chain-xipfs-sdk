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

    private String dataHash;
    private PrivacyStrategy privacyStrategy;
    private String digest;
    private StoreType storeType;

    DownloadDataParameterBuilder(String dataHash) {
        checkParameter(dataHash != null, "dataHash is required");

        this.dataHash = dataHash;
    }

    public DownloadDataParameterBuilder digest(String digest) {
        this.digest = digest;
        return this;
    }

    public DownloadDataParameterBuilder storeType(StoreType storeType) {
        this.storeType = storeType;
        return this;
    }

    public DownloadDataParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    public DownloadDataParameterBuilder plainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create(null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithNemKeysPrivacyStrategy(String privateKey, String publicKey) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey, null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithPasswordPrivacyStrategy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                null, secretParts);
        return this;
    }

    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                List<SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public DownloadDataParameter build() {
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create(null);
        if (this.storeType == null)
            this.storeType = StoreType.RESOURCE;
        return new DownloadDataParameter(dataHash, privacyStrategy, digest, storeType);
    }

}
