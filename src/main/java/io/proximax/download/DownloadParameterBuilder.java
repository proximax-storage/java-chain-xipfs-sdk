package io.proximax.download;

import io.nem.core.crypto.PrivateKey;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This builder class creates the DownloadParameter
 * @see DownloadParameter
 */
public class DownloadParameterBuilder {

    private final String transactionHash;
    private String accountPrivateKey;
    private PrivacyStrategy privacyStrategy;
    private Boolean validateDigest;

    /**
     * Construct the builder class with transaction hash
     * @param transactionHash the blockchain transaction hash to download
     */
    public DownloadParameterBuilder(String transactionHash) {
        checkParameter(transactionHash != null, "transactionHash is required");

        this.transactionHash = transactionHash;
    }

    /**
     * Set the account private key of either sender or recipient of the transaction (required for secure messages)
     * @param accountPrivateKey the account private key
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder withAccountPrivateKey(String accountPrivateKey) {
        checkParameter(() -> accountPrivateKey == null || PrivateKey.fromHexString(accountPrivateKey) != null,
                "accountPrivateKey should be a valid private key");

        this.accountPrivateKey = accountPrivateKey;
        return this;
    }

    /**
     * Set the flag that indicates if need to verify digest
     * @param validateDigest the validate digest flag
     * @return the validate digest flag
     */
    public DownloadParameterBuilder withValidateDigest(Boolean validateDigest) {
        this.validateDigest = validateDigest;
        return this;
    }

    /**
     * Set the privacy strategy to decrypt the data
     * <br>
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param privacyStrategy the privacy strategy
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder withPrivacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    /**
     * Set the privacy strategy as plain
     * <br>
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder withPlainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create();
        return this;
    }

    /**
     * Set the privacy strategy as secured with nem keys
     * <br>
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param privateKey the private key of one blockchain account that encrypted the data
     * @param publicKey the public key of the other blockchain account that encrypted the data
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder withNemKeysPrivacy(String privateKey, String publicKey) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey);
        return this;
    }

    /**
     * Set the privacy strategy as secured with password
     * <br>
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param password a 50-character minimum password
     * @return the same instance of this builder
     */
    public DownloadParameterBuilder withPasswordPrivacy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password);
        return this;
    }

    // TODO - revisit shamir secret sharing implementation that works cross-sdk
//    /**
//     * Set the privacy strategy as secured with shamir secret sharing
//     * <br>
//     * <br>
//     * Privacy strategy defines how the data will be decrypted
//     * @param secretTotalPartCount the total count of parts of the secret
//     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
//     * @param secretParts the array of secret parts composed of the part index and the secret part
//     * @return the same instance of this builder
//     */
//    public DownloadParameterBuilder withShamirSecretSharingPrivacy(int secretTotalPartCount,
//                                                                   int secretMinimumPartCountToBuild,
//                                                                   SecretPart... secretParts) {
//        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
//                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
//        return this;
//    }
//
//    /**
//     * Set the privacy strategy as secured with shamir secret sharing
//     * <br>
//     * <br>
//     * Privacy strategy defines how the data will be decrypted
//     * @param secretTotalPartCount the total count of parts of the secret
//     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
//     * @param secretParts the list of secret parts composed of the part index and the secret part
//     * @return the same instance of this builder
//     */
//    public DownloadParameterBuilder withShamirSecretSharingPrivacy(int secretTotalPartCount,
//                                                                   int secretMinimumPartCountToBuild,
//                                                                   List<SecretPart> secretParts) {
//        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
//                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
//        return this;
//    }
//
//    /**
//     * Set the privacy strategy as secured with shamir secret sharing
//     * <br>
//     * <br>
//     * Privacy strategy defines how the data will be decrypted
//     * @param secretTotalPartCount the total count of parts of the secret
//     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
//     * @param secretParts the map containing part index and secret part pairs
//     * @return the same instance of this builder
//     */
//    public DownloadParameterBuilder withShamirSecretSharingPrivacy(int secretTotalPartCount,
//                                                                   int secretMinimumPartCountToBuild,
//                                                                   Map<Integer, byte[]> secretParts) {
//        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
//                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
//        return this;
//    }

    /**
     * Builds the DownloadParameter
     * <br>
     * Defaults the following if not provided
     * <ul>
     *     <li><b>privacyStrategy</b> - to plain privacy strategy</li>
     *     <li><b>validateDigest</b> - to false</li>
     * </ul>
     * @return the download data parameter
     */
    public DownloadParameter build() {
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create();
        if (this.validateDigest == null)
            this.validateDigest = false;
        return new DownloadParameter(transactionHash, accountPrivateKey, privacyStrategy, validateDigest);
    }

}
