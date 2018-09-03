package io.proximax.privacy.strategy;

import com.codahale.shamir.Scheme;
import io.proximax.cipher.BinaryPBKDF2CipherEncryption;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.PrivacyType;

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
    private final BinaryPBKDF2CipherEncryption encryptor;

    SecuredWithShamirSecretSharingPrivacyStrategy(BinaryPBKDF2CipherEncryption encryptor,
                                                  int secretTotalPartCount, int secretMinimumPartCountToBuild,
                                                  Map<Integer, byte[]> secretParts) {

        checkParameter(secretTotalPartCount > 0, "secretTotalPartCount should be a positive number");
    	checkParameter(secretMinimumPartCountToBuild > 0 && secretMinimumPartCountToBuild <= secretTotalPartCount,
                "secretMinimumPartCountToBuild should be a positive number less than or equal to secretTotalPartCount");
    	checkParameter(secretParts != null, "secretParts is required");
        checkParameter(secretParts.size() >= secretMinimumPartCountToBuild,
                "secretParts should meet minimum part count as defined by secretMinimumPartCountToBuild");

        this.secret = new String(Scheme.of(secretTotalPartCount, secretMinimumPartCountToBuild).join(secretParts)).toCharArray();
        this.encryptor = encryptor;
    }

    /**
     * Get the privacy type which is set as SHAMIR
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.SHAMIR.getValue();
    }

    /**
     * Encrypt the data using the shamir secret sharing
     * @param data data to encrypt
     * @return the encrypted data
     */
    @Override
    public byte[] encryptData(byte[] data) {
        try {
            return encryptor.encrypt(data, secret);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered encrypting data", e);
        }
    }

    /**
     * Decrypt the data using the shamir secret sharing
     * @param data encrypted data to decrypt
     * @return the decrypted data
     */
    @Override
    public byte[] decryptData(byte[] data) {
        try {
            return encryptor.decrypt(data, secret);
        } catch (Exception e) {
            throw new DecryptionFailureException("Exception encountered decrypting data", e);
        }
    }

    /**
     * A model class to represent a secret part which is composed of index and the secret part data
     */
    public static class SecretPart {

        private final int index;
        private final byte[] secretPart;

        /**
         * Construct instance of this model
         * @param index the index of the secret part
         * @param secretPart the data of the secret part
         */
        public SecretPart(int index, byte[] secretPart) {
            this.index = index;
            this.secretPart = secretPart;
        }

        /**
         * Construct instance of this model
         * @param index the index of the secret part
         * @param secretPart the data of the secret part
         * @return instance of this model
         */
        public static SecretPart secretPart(int index, byte[] secretPart) {
            return new SecretPart(index, secretPart);
        }
    }

    /**
     * Create instance of this strategy using an array of secret parts
     * @param secretTotalPartCount the total count of secret parts
     * @param secretMinimumPartCountToBuild the minimum count of parts to build secret
     * @param secretParts array of secret parts
     * @return the instance of this strategy
     */
    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                       int secretMinimumPartCountToBuild,
                                                                       SecretPart... secretParts) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new BinaryPBKDF2CipherEncryption(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                Stream.of(secretParts).collect(Collectors.toMap(parts -> parts.index, parts -> parts.secretPart)));
    }

    /**
     * Create instance of this strategy using a list of secret parts
     * @param secretTotalPartCount the total count of secret parts
     * @param secretMinimumPartCountToBuild the minimum count of parts to build secret
     * @param secretParts list of secret parts
     * @return the instance of this strategy
     */
    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                       int secretMinimumPartCountToBuild,
                                                                       List<SecretPart> secretParts) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new BinaryPBKDF2CipherEncryption(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts == null ? Collections.emptyMap() :
                        secretParts.stream().collect(Collectors.toMap(parts -> parts.index, parts -> parts.secretPart)));
    }

    /**
     * Create instance of this strategy using a map of secret parts
     * @param secretTotalPartCount the total count of secret parts
     * @param secretMinimumPartCountToBuild the minimum count of parts to build secret
     * @param secretParts map of secret parts
     * @return the instance of this strategy
     */
    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                       int secretMinimumPartCountToBuild,
                                                                       Map<Integer, byte[]> secretParts) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new BinaryPBKDF2CipherEncryption(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts == null ? Collections.emptyMap() : secretParts);
    }

}
