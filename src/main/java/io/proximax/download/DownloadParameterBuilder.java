package io.proximax.download;

import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;

import java.util.List;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
/**
 * This builder class creates the DownloadParameter
 * <ul>
 *     <li><b>transactionHash</b> - the blockchain transaction hash to download</li>
 *     <li><b>rootDataHash</b> - the data hash to root data to download</li>
 *     <li><b>digest</b> - the digest to verify the downloaded root data</li>
 *     <li><b>privacyStrategy</b> - the privacy strategy to decrypt the data</li>
 * </ul>
 * @see DownloadParameter
 */

public class DownloadParameterBuilder {

    private String transactionHash;
    private String rootDataHash;
    private PrivacyStrategy privacyStrategy;
    private String digest;

    /**
     * Construct the builder class
     * @param transactionHash the blockchain transaction hash to download
     */
    public DownloadParameterBuilder(String transactionHash) {
        checkParameter(transactionHash != null, "transactionHash is required");

        this.transactionHash = transactionHash;
    }

    /**
     * Construct the builder class
     * @param rootDataHash the data hash to root data to download
     * @param digest an optional digest to verify the downloaded root data
     */
    public DownloadParameterBuilder(String rootDataHash, String digest) {
        checkParameter(rootDataHash != null, "rootDataHash is required");

        this.rootDataHash = rootDataHash;
        this.digest = digest;
    }

    /**
     * Set the privacy strategy to decrypt the data
     * @param privacyStrategy the privacy strategy
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    /**
     * Set the privacy strategy as plain
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder plainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create(null);
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
    public DownloadParameterBuilder securedWithNemKeysPrivacyStrategy(String privateKey, String publicKey) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey, null);
        return this;
    }

    /**
     * Set the privacy strategy as secured with password
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param password a 50-character minimum password
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder securedWithPasswordPrivacyStrategy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, null);
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
    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                null, secretParts);
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
    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      List<SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
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
    public DownloadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                      int secretMinimumPartCountToBuild,
                                                                                      Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    /**
     * Builds the DownloadParameter
     * <br>
     * Defaults the following if not provided
     * <ul>
     *     <li><b>privacyStrategy</b> - to plain privacy strategy</li>
     * </ul>
     * @return the download data parameter
     */
    public DownloadParameter build() {
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create(null);
        return new DownloadParameter(transactionHash, rootDataHash, privacyStrategy, digest);
    }

}
