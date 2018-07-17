package io.proximax.privacy.strategy;

import io.proximax.cipher.BinaryPBKDF2CipherEncryption;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.PrivacyType;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public final class SecuredWithPasswordPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    private static final int MINIMUM_PASSWORD_LENGTH = 50;

    private final BinaryPBKDF2CipherEncryption encryptor;

    private final char[] password;

    SecuredWithPasswordPrivacyStrategy(BinaryPBKDF2CipherEncryption encryptor, String password, String searchTag) {
        super(searchTag);

        checkParameter(password != null, "password is required");
        checkParameter(password.length() >= MINIMUM_PASSWORD_LENGTH, "minimum length for password is 50");

        this.encryptor = encryptor;
        this.password = password.toCharArray();
    }


    @Override
    public int getPrivacyType() {
        return PrivacyType.PASSWORD.getValue();
    }

    @Override
    public final byte[] encryptData(byte[] data) {
        try {
            return encryptor.encrypt(data, password);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered encrypting data", e);
        }
    }

    @Override
    public final byte[] decryptData(byte[] data) {
        try {
            return encryptor.decrypt(data, password);
        } catch (Exception e) {
            throw new DecryptionFailureException("Exception encountered decrypting data", e);
        }
    }

    public static SecuredWithPasswordPrivacyStrategy create(String password, String searchTag) {
        return new SecuredWithPasswordPrivacyStrategy(new BinaryPBKDF2CipherEncryption(), password, searchTag);
    }
}
