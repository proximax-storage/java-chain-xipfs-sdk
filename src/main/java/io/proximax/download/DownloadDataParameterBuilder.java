package io.proximax.download;

import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart;

import java.util.List;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This builder class creates the DownloadDataParameter
 * <ul>
 *     <li><b>dataHash</b> - the data hash to download</li>
 *     <li><b>digest</b> - the digest to verify the downloaded data</li>
 *     <li><b>privacyStrategy</b> - the privacy strategy to decrypt the data</li>
 * </ul>
 * @see DownloadDataParameter
 */
public class DownloadDataParameterBuilder {

    private String dataHash;
    private PrivacyStrategy privacyStrategy;
    private String digest;

    /**
     * Construct the builder class
     * @param dataHash the data hash to download
     */
    public DownloadDataParameterBuilder(String dataHash) {
        checkParameter(dataHash != null, "dataHash is required");

        this.dataHash = dataHash;
    }

    /**
     * Set a digest to verify the downloaded data
     * @param digest the digest
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder digest(String digest) {
        this.digest = digest;
        return this;
    }

    /**
     * Set the privacy strategy to decrypt the data
     * @param privacyStrategy the privacy strategy
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    /**
     * Set the privacy strategy as plain
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder plainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create();
        return this;
    }

    /**
     * Set the privacy strategy as secured with nem keys
     * <br>
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param privateKey the private key of the blockchain account
     * @param publicKey the public key of the blockchain account
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder securedWithNemKeysPrivacy(String privateKey, String publicKey) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey);
        return this;
    }

    /**
     * Set the privacy strategy as secured with password
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param password a 50-character minimum password
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder securedWithPasswordPrivacy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param secretParts the array of secret parts composed of the part index and the secret part
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacy(int secretTotalPartCount,
                                                                              int secretMinimumPartCountToBuild,
                                                                              SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param secretParts the list of secret parts composed of the part index and the secret part
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacy(int secretTotalPartCount,
                                                                              int secretMinimumPartCountToBuild,
                                                                              List<SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param secretParts the map containing part index and secret part pairs
     * @return the same instance of this builder
     */
    public DownloadDataParameterBuilder securedWithShamirSecretSharingPrivacy(int secretTotalPartCount,
                                                                              int secretMinimumPartCountToBuild,
                                                                              Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
        return this;
    }

    /**
     * Builds the DownloadDataParameter
     * <br>
     * Defaults the following if not provided
     * <ul>
     *     <li><b>privacyStrategy</b> - to plain privacy strategy</li>
     * </ul>
     * @return the download data parameter
     */
    public DownloadDataParameter build() {
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create();
        return new DownloadDataParameter(dataHash, privacyStrategy, digest);
    }

}
