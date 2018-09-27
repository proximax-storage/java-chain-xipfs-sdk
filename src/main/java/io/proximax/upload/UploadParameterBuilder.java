package io.proximax.upload;

import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.nem.sdk.model.account.Address;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This builder class creates the UploadParameter
 * @see UploadParameter
 */
public class UploadParameterBuilder {

    private final UploadParameterData data;
    private final String signerPrivateKey;
    private String recipientPublicKey;
    private String recipientAddress;
    private Boolean computeDigest;
    private Boolean detectContentType;
    private Integer transactionDeadline;
    private Boolean useBlockchainSecureMessage;
    private PrivacyStrategy privacyStrategy;

    /**
     * Construct the builder class
     * @param data the data which contains the data and additional info
     * @param signerPrivateKey the private key of a blockchain account that will be used to create transaction for each upload
     */
    public UploadParameterBuilder(UploadParameterData data, String signerPrivateKey) {
        checkParameter(data != null, "data is required");
        checkParameter(signerPrivateKey != null, "signerPrivateKey is required");
        checkParameter(() -> PrivateKey.fromHexString(signerPrivateKey) != null,
                "signerPrivateKey should be a valid private key");

        this.data = data;
        this.signerPrivateKey = signerPrivateKey;
    }

    /**
     * Set the recipient public key
     * @param recipientPublicKey the public key of a blockchain account that will receive the transactions being created (if different from signer)
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withRecipientPublicKey(String recipientPublicKey) {
        checkParameter(() -> recipientPublicKey == null || PublicKey.fromHexString(recipientPublicKey) != null,
                "recipientPublicKey should be a valid public key");

        this.recipientPublicKey = recipientPublicKey;
        return this;
    }

    /**
     * Set the recipient address
     * @param recipientAddress the address of a blockchain account that will receive the transactions being created (if different from signer)
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withRecipientAddress(String recipientAddress) {
        checkParameter(() -> recipientAddress == null || Address.createFromRawAddress(recipientAddress) != null,
                "recipientAddress should be a valid address");

        this.recipientAddress = recipientAddress;
        return this;
    }

    /**
     * Set the compute digest flag
     * @param computeDigest flag that indicates if a digest is required to be calculated
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withComputeDigest(Boolean computeDigest) {
        this.computeDigest = computeDigest;
        return this;
    }

    /**
     * Set the detect content type flag
     * @param detectContentType flag that indicates if a content type is to be derived
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withDetectContentType(Boolean detectContentType) {
        this.detectContentType = detectContentType;
        return this;
    }

    /**
     * Set the use blockchain secure message flag
     * @param useBlockchainSecureMessage flag that indicates if transaction's message is to be secured
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withUseBlockchainSecureMessage(Boolean useBlockchainSecureMessage) {
        this.useBlockchainSecureMessage = useBlockchainSecureMessage;
        return this;
    }

    /**
     * Set the transaction deadline
     * @param transactionDeadline transaction deadline (duration) for the blockchain transaction to be created. This value is in hours.
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withTransactionDeadline(Integer transactionDeadline) {
        checkParameter(transactionDeadline == null || (transactionDeadline >= 1 && transactionDeadline <= 23),
                "transactionDeadline should be between 1 and 23");

        this.transactionDeadline = transactionDeadline;
        return this;
    }

    /**
     * Set the privacy strategy
     * <br>
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param privacyStrategy strategy that defines how the data will be encrypted
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withPrivacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    /**
     * Set the privacy strategy as plain
     * <br>
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withPlainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create();
        return this;
    }

    /**
     * Set the privacy strategy as secured with two nem keys
     * <br>
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param privateKey the private key of one blockchain account to encrypt the data
     * @param publicKey the public key of the other blockchain account to encrypt the data
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withNemKeysPrivacy(String privateKey, String publicKey) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(privateKey, publicKey);
        return this;
    }

    /**
     * Set the privacy strategy as secured with password
     * <br>
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param password a 50-character minimum password
     * @return the same instance of this builder
     */
    public UploadParameterBuilder withPasswordPrivacy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password);
        return this;
    }

    // TODO - revisit shamir secret sharing implementation that works cross-sdk
//    /**
//     * Set the privacy strategy as secured with shamir secret sharing
//     * <br>
//     * <br>
//     * Privacy strategy defines how the data will be encrypted
//     * @param secretTotalPartCount the total count of parts of the secret
//     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
//     * @param secretParts the array of secret parts composed of the part index and the secret part
//     * @return the same instance of this builder
//     */
//    public UploadParameterBuilder withShamirSecretSharing(int secretTotalPartCount,
//                                                          int secretMinimumPartCountToBuild,
//                                                          SecretPart... secretParts) {
//        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
//                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
//        return this;
//    }
//
//    /**
//     * Set the privacy strategy as secured with shamir secret sharing
//     * <br>
//     * <br>
//     * Privacy strategy defines how the data will be encrypted
//     * @param secretTotalPartCount the total count of parts of the secret
//     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
//     * @param secretParts the list of secret parts composed of the part index and the secret part
//     * @return the same instance of this builder
//     */
//    public UploadParameterBuilder withShamirSecretSharing(int secretTotalPartCount,
//                                                          int secretMinimumPartCountToBuild,
//                                                          List<SecretPart> secretParts) {
//        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
//                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
//        return this;
//    }
//
//    /**
//     * Set the privacy strategy as secured with shamir secret sharing
//     * <br>
//     * <br>
//     * Privacy strategy defines how the data will be encrypted
//     * @param secretTotalPartCount the total count of parts of the secret
//     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
//     * @param secretParts the map containing part index and secret part pairs
//     * @return the same instance of this builder
//     */
//    public UploadParameterBuilder withShamirSecretSharing(int secretTotalPartCount,
//                                                          int secretMinimumPartCountToBuild,
//                                                          Map<Integer, byte[]> secretParts) {
//        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
//                secretTotalPartCount, secretMinimumPartCountToBuild, secretParts);
//        return this;
//    }

    /**
     * Builds the UploadParameter
     * <br>
     * Defaults the following if not provided
     * <ul>
     *     <li><b>computeDigest</b> - to false</li>
     *     <li><b>detectContentType</b> - to false</li>
     *     <li><b>transactionDeadline</b> - to 12</li>
     *     <li><b>useBlockchainSecureMessage</b> - to false</li>
     *     <li><b>privacyStrategy</b> - to plain privacy strategy</li>
     * </ul>
     * @return the upload parameter
     */
    public UploadParameter build() {
        if (this.computeDigest == null)
            this.computeDigest = false;
        if (this.detectContentType == null)
            this.detectContentType = false;
        if (this.transactionDeadline == null)
            this.transactionDeadline = 12;
        if (this.useBlockchainSecureMessage == null)
            this.useBlockchainSecureMessage = false;
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create();

        return new UploadParameter(data, signerPrivateKey, recipientPublicKey, recipientAddress, computeDigest, detectContentType,
                transactionDeadline, useBlockchainSecureMessage, privacyStrategy);
    }

}
