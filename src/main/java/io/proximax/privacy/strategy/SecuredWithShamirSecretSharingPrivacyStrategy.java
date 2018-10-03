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
package io.proximax.privacy.strategy;

import com.codahale.shamir.Scheme;
import io.proximax.cipher.PBECipherEncryptor;
import io.proximax.model.PrivacyType;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The privacy strategy that secures the data using shamir secret sharing.
 * <br>
 * <br>
 * This strategy requires the total count of secret parts, minimum count of parts to build secret, and the secret parts.
 */
public final class SecuredWithShamirSecretSharingPrivacyStrategy extends PrivacyStrategy {

    private final char[] secret;
    private final PBECipherEncryptor pbeCipherEncryptor;

    SecuredWithShamirSecretSharingPrivacyStrategy(PBECipherEncryptor pbeCipherEncryptor,
                                                  int secretTotalPartCount, int secretMinimumPartCountToBuild,
                                                  Map<Integer, byte[]> secretParts) {

        checkParameter(secretTotalPartCount > 0, "secretTotalPartCount should be a positive number");
        checkParameter(secretMinimumPartCountToBuild > 0 && secretMinimumPartCountToBuild <= secretTotalPartCount,
                "secretMinimumPartCountToBuild should be a positive number less than or equal to secretTotalPartCount");
        checkParameter(secretParts != null, "secretParts is required");
        checkParameter(secretParts.size() >= secretMinimumPartCountToBuild,
                "secretParts should meet minimum part count as defined by secretMinimumPartCountToBuild");

        this.secret = new String(Scheme.of(secretTotalPartCount, secretMinimumPartCountToBuild).join(secretParts)).toCharArray();
        this.pbeCipherEncryptor = pbeCipherEncryptor;
    }

    /**
     * Get the privacy type which is set as SHAMIR
     *
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.SHAMIR.getValue();
    }

    /**
     * Encrypt byte stream using the shamir secret sharing
     *
     * @param byteStream the byte stream to encrypt
     * @return the encrypted byte stream
     */
    @Override
    public final InputStream encryptStream(final InputStream byteStream) {
        return pbeCipherEncryptor.encryptStream(byteStream, secret);
    }

    /**
     * Decrypt byte stream using the shamir secret sharing
     *
     * @param byteStream the byte stream to decrypt
     * @return the decrypted byte stream
     */
    @Override
    public final InputStream decryptStream(final InputStream byteStream) {
        return pbeCipherEncryptor.decryptStream(byteStream, secret);
    }

    /**
     * A model class to represent a secret part which is composed of index and the secret part data
     */
    public static class SecretPart {

        private final int index;
        private final byte[] secretPart;

        /**
         * Construct instance of this model
         *
         * @param index      the index of the secret part
         * @param secretPart the data of the secret part
         */
        public SecretPart(int index, byte[] secretPart) {
            this.index = index;
            this.secretPart = secretPart;
        }

        /**
         * Construct instance of this model
         *
         * @param index      the index of the secret part
         * @param secretPart the data of the secret part
         * @return instance of this model
         */
        public static SecretPart secretPart(int index, byte[] secretPart) {
            return new SecretPart(index, secretPart);
        }
    }

    /**
     * Create instance of this strategy using an array of secret parts
     *
     * @param secretTotalPartCount          the total count of secret parts
     * @param secretMinimumPartCountToBuild the minimum count of parts to build secret
     * @param secretParts                   array of secret parts
     * @return the instance of this strategy
     */
    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                       int secretMinimumPartCountToBuild,
                                                                       SecretPart... secretParts) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new PBECipherEncryptor(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                Stream.of(secretParts).collect(Collectors.toMap(parts -> parts.index, parts -> parts.secretPart)));
    }

    /**
     * Create instance of this strategy using a list of secret parts
     *
     * @param secretTotalPartCount          the total count of secret parts
     * @param secretMinimumPartCountToBuild the minimum count of parts to build secret
     * @param secretParts                   list of secret parts
     * @return the instance of this strategy
     */
    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                       int secretMinimumPartCountToBuild,
                                                                       List<SecretPart> secretParts) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new PBECipherEncryptor(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts == null ? Collections.emptyMap() :
                        secretParts.stream().collect(Collectors.toMap(parts -> parts.index, parts -> parts.secretPart)));
    }

    /**
     * Create instance of this strategy using a map of secret parts
     *
     * @param secretTotalPartCount          the total count of secret parts
     * @param secretMinimumPartCountToBuild the minimum count of parts to build secret
     * @param secretParts                   map of secret parts
     * @return the instance of this strategy
     */
    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                       int secretMinimumPartCountToBuild,
                                                                       Map<Integer, byte[]> secretParts) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new PBECipherEncryptor(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts == null ? Collections.emptyMap() : secretParts);
    }

}
