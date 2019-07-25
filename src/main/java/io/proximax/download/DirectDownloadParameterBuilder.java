package io.proximax.download;

import io.ipfs.multihash.Multihash;
import io.proximax.core.crypto.PrivateKey;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.NemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.PasswordPrivacyStrategy;

import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This builder class creates the DirectDownloadParameter
 * @see DirectDownloadParameter
 */
public class DirectDownloadParameterBuilder {

    private String transactionHash;
    private String accountPrivateKey;
    private String dataHash;
    private Boolean validateDigest;
    private PrivacyStrategy privacyStrategy;
    private String digest;

    private DirectDownloadParameterBuilder() {
    }

    /**
     * Construct the builder class with transaction hash, account private key and validate digest flag
     * @param transactionHash the transaction hash of target download
     * @param accountPrivateKey the private key of the sender or recipient of the transaction (required when downloading from secure message)
     * @param validateDigest the validate digest flag as to whether to verify data with digest
     * @return the direct download parameter builder
     */
    public static DirectDownloadParameterBuilder createFromTransactionHash(String transactionHash, String accountPrivateKey, Boolean validateDigest) {
        checkParameter(transactionHash != null, "transactionHash is required");
        checkParameter(() -> accountPrivateKey == null || PrivateKey.fromHexString(accountPrivateKey) != null,
                "accountPrivateKey should be a valid private key");

        final DirectDownloadParameterBuilder builder = new DirectDownloadParameterBuilder();
        builder.transactionHash = transactionHash;
        builder.accountPrivateKey = accountPrivateKey;
        builder.validateDigest = Optional.ofNullable(validateDigest).orElse(false);
        return builder;
    }

    /**
     * Construct the builder class with data hash and digest
     * @param dataHash the data hash to download
     * @param digest the digest to verify download
     * @return the download data parameter builder
     */
    public static DirectDownloadParameterBuilder createFromDataHash(String dataHash, String digest) {
        checkParameter(dataHash != null, "dataHash is required");
        checkParameter(() -> Multihash.fromBase58(dataHash) != null,
                "dataHash should be a valid ipfs hash");

        final DirectDownloadParameterBuilder builder = new DirectDownloadParameterBuilder();
        builder.dataHash = dataHash;
        builder.digest = digest;
        builder.validateDigest = true;
        return builder;
    }

    /**
     * Set the privacy strategy to decrypt the data
     * <br>
     * <br>
     * Privacy strategy defines how the data will be decrypted
     * @param privacyStrategy the privacy strategy
     * @return the same instance of this builder
     */
    public DirectDownloadParameterBuilder withPrivacyStrategy(PrivacyStrategy privacyStrategy) {
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
    public DirectDownloadParameterBuilder withPlainPrivacy() {
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
    public DirectDownloadParameterBuilder withNemKeysPrivacy(String privateKey, String publicKey) {
        this.privacyStrategy = NemKeysPrivacyStrategy.create(privateKey, publicKey);
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
    public DirectDownloadParameterBuilder withPasswordPrivacy(String password) {
        this.privacyStrategy = PasswordPrivacyStrategy.create(password);
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
//    public DirectDownloadParameterBuilder withShamirSecretSharingPrivacy(int secretTotalPartCount,
//                                                                         int secretMinimumPartCountToBuild,
//                                                                         SecretPart... secretParts) {
//        this.privacyStrategy = ShamirSecretSharingPrivacyStrategy.create(
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
//    public DirectDownloadParameterBuilder withShamirSecretSharingPrivacy(int secretTotalPartCount,
//                                                                         int secretMinimumPartCountToBuild,
//                                                                         List<SecretPart> secretParts) {
//        this.privacyStrategy = ShamirSecretSharingPrivacyStrategy.create(
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
//    public DirectDownloadParameterBuilder withShamirSecretSharingPrivacy(int secretTotalPartCount,
//                                                                         int secretMinimumPartCountToBuild,
//                                                                         Map<Integer, byte[]> secretParts) {
//        this.privacyStrategy = ShamirSecretSharingPrivacyStrategy.create(
//                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
//        return this;
//    }

    /**
     * Builds the DirectDownloadParameter
     * <br>
     * Defaults the following if not provided
     * <ul>
     *     <li><b>privacyStrategy</b> - to plain privacy strategy</li>
     * </ul>
     * @return the download data parameter
     */
    public DirectDownloadParameter build() {
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create();
        return new DirectDownloadParameter(transactionHash, accountPrivateKey, dataHash, validateDigest, privacyStrategy, digest);
    }

}
