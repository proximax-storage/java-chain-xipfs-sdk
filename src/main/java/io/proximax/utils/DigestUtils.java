/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     *
     * @param byteStream the byteStream
     * @return the sha-256 hex of the data
     */
    public Observable<String> digest(InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return Observable.just(encodeData(byteStream));
    }

    /**
     * Validate the digest against the given data
     *
     * @param byteStream     the byteStream
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
