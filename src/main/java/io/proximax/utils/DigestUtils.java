package io.proximax.utils;

import io.proximax.exceptions.DigestDoesNotMatchException;
import io.reactivex.Observable;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static io.proximax.utils.CollectionUtils.isEqualList;
import static io.proximax.utils.CollectionUtils.nonNullCount;
import static java.lang.String.format;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

/**
 * The utility class for calculating and validating digests
 */
public class DigestUtils {

    /**
     * Compute for the digest of the given data
     * @param data the data
     * @return the sha-256 hex of the data
     */
    public Observable<String> digest(byte[] data) {
        checkArgument(data != null, "data is required");

        return Observable.just(encodeData(data));
    }

    /**
     * Validate the digest against the given data
     * @param data the data
     * @param expectedDigest the expected digest of the data
     * @return true if digest validation passes, otherwise an DigestDoesNotMatchException
     * @see DigestDoesNotMatchException
     */
    public Observable<Boolean> validateDigest(byte[] data, String expectedDigest) {
        checkArgument(data != null, "data is required");

        if (expectedDigest != null) {
            return digest(data).map(actualDigest -> {
                if (!actualDigest.equals(expectedDigest)) {
                    throw new DigestDoesNotMatchException(format("Data digests do not match (actual: %s, expected %s)",
                            actualDigest, expectedDigest));
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
