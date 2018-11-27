package io.proximax.utils;

import io.proximax.exceptions.DigestCalculationFailureException;
import io.proximax.exceptions.DigestDoesNotMatchException;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.lang.String.format;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

/**
 * The utility class for calculating and validating digests
 */
public class DigestUtils {

    private DigestUtils() {

    }

    /**
     * Compute for the digest of the given data
     * @param inputStream the inputStream
     * @return the sha-256 hex of the data
     */
    public static String digest(InputStream inputStream) {
        checkParameter(inputStream != null, "byteStream is required");

        return encodeData(inputStream);
    }

    /**
     * Validate the digest against the given data
     * @param inputStream the inputStream
     * @param expectedDigest the expected digest of the data
     * @return true if digest validation passes, otherwise an DigestDoesNotMatchException
     * @see DigestDoesNotMatchException
     */
    public static boolean validateDigest(InputStream inputStream, String expectedDigest) {
        checkParameter(inputStream != null, "inputStream is required");

        if (expectedDigest != null) {
            try (InputStream stream = inputStream) {
                final String actualDigest = digest(stream);
                if (!actualDigest.equals(expectedDigest)) {
                    throw new DigestDoesNotMatchException(format("Data digests do not match (actual: %s, expected %s)",
                            actualDigest, expectedDigest));
                } else {
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to calculate digest of stream", e);
            }
        }
        return true;
    }

    private static String encodeData(InputStream byteStream) {
        try {
            return sha256Hex(byteStream);
        } catch (IOException e) {
            throw new DigestCalculationFailureException("Digest calculation failed", e);
        }
    }
}
