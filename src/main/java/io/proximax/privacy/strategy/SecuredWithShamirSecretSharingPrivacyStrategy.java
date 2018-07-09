package io.proximax.privacy.strategy;

import com.codahale.shamir.Scheme;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.cipher.BinaryPBKDF2CipherEncryption;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.ProximaxChildMessage;
import io.proximax.utils.ParameterValidationUtils;

import java.util.Map;


public final class SecuredWithShamirSecretSharingPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    private final char[] secret;
    private final BinaryPBKDF2CipherEncryption encryptor;

    public SecuredWithShamirSecretSharingPrivacyStrategy(BinaryPBKDF2CipherEncryption encryptor,
                                                         int secretTotalPartCount, int secretMinimumPartCountToBuild,
                                                         Map<Integer, byte[]> secretParts) {
    	ParameterValidationUtils.checkParameter(secretTotalPartCount > 0, "secretTotalPartCount should be a positive number");
    	ParameterValidationUtils.checkParameter(secretMinimumPartCountToBuild > 0 && secretMinimumPartCountToBuild <= secretTotalPartCount,
                "secretMinimumPartCountToBuild should be a positive number less than or equal to secretTotalPartCount");
    	ParameterValidationUtils.checkParameter(secretParts != null, "secretParts is required");
        ParameterValidationUtils.checkParameter(secretParts.size() >= secretMinimumPartCountToBuild,
                "secretParts should meet minimum part count as defined by secretMinimumPartCountToBuild");

        this.secret = new String(Scheme.of(secretTotalPartCount, secretMinimumPartCountToBuild).join(secretParts)).toCharArray();
        this.encryptor = encryptor;
    }

    @Override
    public byte[] encrypt(final byte[] data) {
        try {
            return encryptor.encrypt(data, secret);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered encrypting data", e);
        }
    }

    @Override
    public byte[] decrypt(final byte[] data, final TransferTransaction transaction, final ProximaxChildMessage message) {
        try {
            return encryptor.decrypt(data, secret);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered decrypting data", e);
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
}
