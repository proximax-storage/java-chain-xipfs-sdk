package io.proximax.utils;

import io.proximax.privacy.strategy.PrivacyStrategy;
import io.reactivex.Observable;

import java.util.List;

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
     * Encrypt a list of data using the given privacy strategy
     * @param privacyStrategy the privacy strategy
     * @param dataList the list of data
     * @return the list of encrypted data
     */
    public Observable<List<byte[]>> encryptList(PrivacyStrategy privacyStrategy, List<byte[]> dataList) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(dataList != null, "dataInput is required");

        return Observable.fromIterable(dataList)
                .map(data -> encryptData(privacyStrategy, data))
                .toList()
                .toObservable();
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

    /**
     * Decrypt a list of encrypted data using the given privacy strategy
     * @param privacyStrategy the privacy strategy
     * @param dataList the list of data
     * @return the list of decrypted data
     */
    public Observable<List<byte[]>> decryptList(PrivacyStrategy privacyStrategy, List<byte[]> dataList) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(dataList != null, "dataInput is required");

        return Observable.fromIterable(dataList)
                .map(data -> decryptData(privacyStrategy, data))
                .toList()
                .toObservable();
    }

    private byte[] encryptData(PrivacyStrategy privacyStrategy, byte[] data) {
        return privacyStrategy.encryptData(data);
    }

    private byte[] decryptData(PrivacyStrategy privacyStrategy, byte[] data) {
        return privacyStrategy.decryptData(data);
    }
}
