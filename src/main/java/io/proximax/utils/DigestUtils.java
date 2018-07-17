package io.proximax.utils;

import io.proximax.exceptions.InvalidDigestException;
import io.reactivex.Observable;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static io.proximax.utils.CollectionUtils.isEqualList;
import static io.proximax.utils.CollectionUtils.nonNullCount;
import static java.lang.String.format;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class DigestUtils {

    public Observable<String> digest(byte[] data) {
        checkArgument(data != null, "data is required");

        return Observable.just(encodeData(data));
    }

    public Observable<List<String>> digestForList(List<byte[]> dataList) {
        checkArgument(dataList != null, "dataList is required");

        return Observable.fromIterable(dataList)
                .concatMapEager(this::digest)
                .toList()
                .toObservable();
    }

    public Observable<Boolean> validateDigest(byte[] data, String expectedDigest) {
        checkArgument(data != null, "data is required");

        if (expectedDigest != null) {
            return digest(data).map(actualDigest -> {
                if (!actualDigest.equals(expectedDigest)) {
                    throw new InvalidDigestException(format("Root data digests do not match (actual: %s, expected %s)",
                            actualDigest, expectedDigest));
                } else {
                    return true;
                }
            });
        }
        return Observable.just(true);
    }

    public Observable<Boolean> validateDigestList(List<byte[]> dataList, List<String> expectedDigestList) {
        checkArgument(dataList != null, "dataList is required");

        if (expectedDigestList != null && nonNullCount(expectedDigestList) == expectedDigestList.size()) {
            return digestForList(dataList).map(actualDigestList -> {
                if (!isEqualList(actualDigestList, expectedDigestList)) {
                    throw new InvalidDigestException("Some data digests do not match");
                } else {
                    return true;
                }
            });
        }
        return Observable.just(true);
    }

    private String encodeData(byte[] data) {
        return sha256Hex(data);
    }
}
