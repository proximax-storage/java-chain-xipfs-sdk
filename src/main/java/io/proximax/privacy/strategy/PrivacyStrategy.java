package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.Message;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The abstract class privacy strategy
 * <br>
 * <br>
 * Privacy strategy handles the encrypting and decrypting of data as well as the encoding and decoding of payload on the blockchain transaction
 * <br>
 * <br>
 * Privacy strategies can be created with privacy search tag to optimize searching by trying out the correct strategy when trying to read a data.
 * Without search tag, searching would fall back to using brute force of guessing the strategy to use to decrypt data.
 * <br>
 * <br>
 * When creating a custom Privacy Strategy, implement CustomPrivacyStrategy
 * @see CustomPrivacyStrategy
 */
public abstract class PrivacyStrategy {

    private final String privacySearchTag;

    PrivacyStrategy(String privacySearchTag) {
        checkParameter(privacySearchTag == null || privacySearchTag.length() <= 50,
                "privacy search tag cannot be more than 50 characters");

        this.privacySearchTag = privacySearchTag;
    }

    /**
     * Get the privacy search tag
     * @return the privacy search tag
     */
    public final String getPrivacySearchTag() {
        return privacySearchTag;
    }

    /**
     * Get the privacy type's int value
     * @return the privacy type's int value
     */
    public abstract int getPrivacyType();

    /**
     * Encrypt data
     * @param data data to encrypt
     * @return encrypted data
     */
    public abstract byte[] encryptData(final byte[] data);

    /**
     * Decrypt data
     * @param data encrypted data to decrypt
     * @return the decrypted data
     */
    public abstract byte[] decryptData(final byte[] data);

    /**
     * Encode payload as transaction message
     * @param payload the payload to be attached as message
     * @return the transaction message
     */
    public abstract Message encodePayloadAsMessage(final String payload);

    /**
     * Decode the payload from tranasction message
     * @param message the transaction message
     * @return the payload
     */
    public abstract String decodePayloadFromMessage(final Message message);

}
