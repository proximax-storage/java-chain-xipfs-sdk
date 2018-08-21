package io.proximax.utils;

import io.proximax.privacy.strategy.PrivacyStrategy;
import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The utility class to verify method parameters
 */
public class PrivacyDataEncryptionUtils {

    public PrivacyDataEncryptionUtils() {
    }

    /**
     * Encrypt the data using the given privacy strategy
     * @param privacyStrategy the privacy strategy
     * @param data the data
     * @return the encrypted data
     */
    public Observable<byte[]> encrypt(PrivacyStrategy privacyStrategy, byte[] data) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(data != null, "data is required");

        return Observable.just(encryptData(privacyStrategy, data));
    }

    /**
     * Decrypt an encrypted data using the given privacy strategy
     * @param privacyStrategy the privacy strategy
     * @param data the data
     * @return the decrypted data
     */
    public Observable<byte[]> decrypt(PrivacyStrategy privacyStrategy, byte[] data) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(data != null, "data is required");

        return Observable.just(decryptData(privacyStrategy, data));
    }

    private byte[] encryptData(PrivacyStrategy privacyStrategy, byte[] data) {
        return privacyStrategy.encryptData(data);
    }

    private byte[] decryptData(PrivacyStrategy privacyStrategy, byte[] data) {
        return privacyStrategy.decryptData(data);
    }
}
