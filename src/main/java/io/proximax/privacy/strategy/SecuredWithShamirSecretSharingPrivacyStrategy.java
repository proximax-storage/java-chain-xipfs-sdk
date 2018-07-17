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


public final class SecuredWithShamirSecretSharingPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    private final char[] secret;
    private final BinaryPBKDF2CipherEncryption encryptor;

    public SecuredWithShamirSecretSharingPrivacyStrategy(BinaryPBKDF2CipherEncryption encryptor,
                                                         int secretTotalPartCount, int secretMinimumPartCountToBuild,
                                                         Map<Integer, byte[]> secretParts, String searchTag) {
        super(searchTag);

        checkParameter(secretTotalPartCount > 0, "secretTotalPartCount should be a positive number");
    	checkParameter(secretMinimumPartCountToBuild > 0 && secretMinimumPartCountToBuild <= secretTotalPartCount,
                "secretMinimumPartCountToBuild should be a positive number less than or equal to secretTotalPartCount");
    	checkParameter(secretParts != null, "secretParts is required");
        checkParameter(secretParts.size() >= secretMinimumPartCountToBuild,
                "secretParts should meet minimum part count as defined by secretMinimumPartCountToBuild");

        this.secret = new String(Scheme.of(secretTotalPartCount, secretMinimumPartCountToBuild).join(secretParts)).toCharArray();
        this.encryptor = encryptor;
    }

    @Override
    public int getPrivacyType() {
        return PrivacyType.SHAMIR.getValue();
    }

    @Override
    public byte[] encryptData(byte[] data) {
        try {
            return encryptor.encrypt(data, secret);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered encrypting data", e);
        }    }

    @Override
    public byte[] decryptData(byte[] data) {
        try {
            return encryptor.decrypt(data, secret);
        } catch (Exception e) {
            throw new DecryptionFailureException("Exception encountered decrypting data", e);
        }
    }

    public static class SecretPart {

        public final int index;
        public final byte[] secretPart;

        public SecretPart(int index, byte[] secretPart) {
            this.index = index;
            this.secretPart = secretPart;
        }

        public static SecretPart secretPart(int index, byte[] secretPart) {
            return new SecretPart(index, secretPart);
        }
    }

    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                int secretMinimumPartCountToBuild,
                                                                String searchTag,
                                                                SecretPart... secretParts) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new BinaryPBKDF2CipherEncryption(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                Stream.of(secretParts).collect(Collectors.toMap(parts -> parts.index, parts -> parts.secretPart)), searchTag);
    }

    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                int secretMinimumPartCountToBuild,
                                                                List<SecretPart> secretParts,
                                                                String searchTag) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new BinaryPBKDF2CipherEncryption(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts == null ? Collections.emptyMap() :
                        secretParts.stream().collect(Collectors.toMap(parts -> parts.index, parts -> parts.secretPart)),
                searchTag);
    }

    public static SecuredWithShamirSecretSharingPrivacyStrategy create(int secretTotalPartCount,
                                                                int secretMinimumPartCountToBuild,
                                                                Map<Integer, byte[]> secretParts,
                                                                String searchTag) {
        return new SecuredWithShamirSecretSharingPrivacyStrategy(new BinaryPBKDF2CipherEncryption(),
                secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts == null ? Collections.emptyMap() : secretParts,
                searchTag);
    }

}
