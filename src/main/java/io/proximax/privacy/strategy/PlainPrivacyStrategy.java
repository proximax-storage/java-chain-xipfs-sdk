package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;

/**
 * The plain privacy strategy.
 * <br>
 * <br>
 * This strategy does not encrypt nor decrypt the data.
 */
public final class PlainPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    PlainPrivacyStrategy(String searchTag) {
        super(searchTag);
    }

    /**
     * Get the privacy type which is set as PLAIN
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.PLAIN.getValue();
    }

    /**
     * Return same data on encrypt
     * @param data data to encrypt
     * @return same data
     */
    @Override
    public final byte[] encryptData(byte[] data) {
        return data;
    }

    /**
     * Return same data on decrypt
     * @param data data to decrypt
     * @return same data
     */
    @Override
    public final byte[] decryptData(byte[] data) {
        return data;
    }

    /**
     * Create instance of this strategy
     * @param searchTag an optional search tag
     * @return the instance of this strategy
     */
    public static PlainPrivacyStrategy create(String searchTag) {
        return new PlainPrivacyStrategy(searchTag);
    }
}
