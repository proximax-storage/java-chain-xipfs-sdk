package io.proximax.privacy.strategy;

/**
 * The abstract class privacy strategy
 * <br>
 * <br>
 * Privacy strategy handles the encrypting and decrypting of data
 * <br>
 * <br>
 * When creating a custom Privacy Strategy, implement CustomPrivacyStrategy
 * @see CustomPrivacyStrategy
 */
public abstract class PrivacyStrategy {

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
}
