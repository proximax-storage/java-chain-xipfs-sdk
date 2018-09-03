package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;

/**
 * The plain privacy strategy.
 * <br>
 * <br>
 * This strategy does not encrypt nor decrypt the data.
 */
public final class PlainPrivacyStrategy extends PrivacyStrategy {

    private PlainPrivacyStrategy() {

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
     * @return the instance of this strategy
     */
    public static PlainPrivacyStrategy create() {
        return new PlainPrivacyStrategy();
    }
}
