package io.proximax.utils;

import io.proximax.exceptions.DigestCalculationFailureException;
import io.proximax.exceptions.DigestDoesNotMatchException;
import io.reactivex.Observable;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.lang.String.format;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

/**
 * The utility class for calculating and validating digests
 */
public class DigestUtils {

    /**
     * Compute for the digest of the given data
     * @param byteStream the byteStream
     * @return the sha-256 hex of the data
     */
    public Observable<String> digest(InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return Observable.just(encodeData(byteStream));
    }

    /**
     * Validate the digest against the given data
     * @param byteStream the byteStream
     * @param expectedDigest the expected digest of the data
     * @return true if digest validation passes, otherwise an DigestDoesNotMatchException
     * @see DigestDoesNotMatchException
     */
    public Observable<Boolean> validateDigest(InputStream byteStream, String expectedDigest) {
        checkParameter(byteStream != null, "byteStream is required");

        if (expectedDigest != null) {
            return digest(byteStream).map(actualDigest -> {
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

    private String encodeData(InputStream byteStream) {
        try {
            return sha256Hex(byteStream);
        } catch (IOException e) {
            throw new DigestCalculationFailureException("Digest calculation failed", e);
        }
    }
}
