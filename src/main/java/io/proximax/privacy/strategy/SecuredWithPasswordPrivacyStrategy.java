package io.proximax.privacy.strategy;

import io.proximax.cipher.PBECipherEncryptor;
import io.proximax.model.PrivacyType;

import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The privacy strategy that secures the data using a long password, 50 characters minimum.
 */
public final class SecuredWithPasswordPrivacyStrategy extends PrivacyStrategy {

    private static final int MINIMUM_PASSWORD_LENGTH = 50;

    private final PBECipherEncryptor pbeCipherEncryptor;

    private final char[] passwordCharArray;
    private final String password;

    SecuredWithPasswordPrivacyStrategy(PBECipherEncryptor pbeCipherEncryptor, String password) {
        checkParameter(password != null, "password is required");
        checkParameter(password.length() >= MINIMUM_PASSWORD_LENGTH, "minimum length for password is 50");

        this.pbeCipherEncryptor = pbeCipherEncryptor;
        this.password = password;
        this.passwordCharArray = password.toCharArray();
    }

    /**
     * Get the privacy type which is set as PASSWORD
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.PASSWORD.getValue();
    }

    /**
     * Encrypt byte stream with password
     * @param byteStream the byte stream to encrypt
     * @return the encrypted byte stream
     */
    @Override
    public final InputStream encryptStream(final InputStream byteStream) {
        return pbeCipherEncryptor.encryptStream(byteStream, passwordCharArray);
    }

    /**
     * Decrypt byte stream with password
     * @param byteStream the byte stream to decrypt
     * @return the decrypted byte stream
     */
    @Override
    public final InputStream decryptStream(final InputStream byteStream) {
        return pbeCipherEncryptor.decryptStream(byteStream, passwordCharArray);

    }

    /**
     * Create instance of this strategy
     * @param password the password
     * @return the instance of this strategy
     */
    public static SecuredWithPasswordPrivacyStrategy create(String password) {
        return new SecuredWithPasswordPrivacyStrategy(new PBECipherEncryptor(), password);
    }
}
