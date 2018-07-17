package io.proximax.utils;

import io.proximax.privacy.strategy.PrivacyStrategy;
import io.reactivex.Observable;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class PrivacyDataEncryptionUtils {

    public PrivacyDataEncryptionUtils() {
    }

    public Observable<byte[]> encrypt(PrivacyStrategy privacyStrategy, byte[] data) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(data != null, "data is required");

        return Observable.just(encryptData(privacyStrategy, data));
    }

    public Observable<List<byte[]>> encryptList(PrivacyStrategy privacyStrategy, List<byte[]> dataList) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(dataList != null, "dataInput is required");

        return Observable.fromIterable(dataList)
                .map(data -> encryptData(privacyStrategy, data))
                .toList()
                .toObservable();
    }

    public Observable<byte[]> decrypt(PrivacyStrategy privacyStrategy, byte[] data) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(data != null, "data is required");

        return Observable.just(decryptData(privacyStrategy, data));
    }

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
