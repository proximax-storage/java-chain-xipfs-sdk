package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.cipher.BinaryPBKDF2CipherEncryption;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.ProximaxChildMessage;
import io.proximax.utils.ParameterValidationUtils;

/**
 * The Class SecuredWithPasswordPrivacyStrategy.
 */
public final class SecuredWithPasswordPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    /** The Constant MINIMUM_PASSWORD_LENGTH. */
    private static final int MINIMUM_PASSWORD_LENGTH = 50;
    
    /** The encryptor. */
    private final BinaryPBKDF2CipherEncryption encryptor;
    
    /** The password. */
    private final char[] password;


    /**
     * Instantiates a new secured with password privacy strategy.
     *
     * @param encryptor the encryptor
     * @param password the password
     */
    public SecuredWithPasswordPrivacyStrategy(BinaryPBKDF2CipherEncryption encryptor, String password) {
        ParameterValidationUtils.checkParameter(password != null, "password is required");
        ParameterValidationUtils.checkParameter(password.length() >= MINIMUM_PASSWORD_LENGTH, "minimum length for password is 50");

        this.encryptor = encryptor;
        this.password = password.toCharArray();
    }

    /* (non-Javadoc)
     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#encrypt(byte[])
     */
    @Override
    public byte[] encrypt(final byte[] data) {
        try {
            return encryptor.encrypt(data, password);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered encrypting data", e);
        }
    }

    /* (non-Javadoc)
     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#decrypt(byte[], org.nem.core.model.TransferTransaction, io.nem.xpx.service.model.buffers.ResourceHashMessage)
     */
    @Override
    public byte[] decrypt(final byte[] data, final TransferTransaction transaction, final ProximaxChildMessage message) {
        try {
            return encryptor.decrypt(data, password);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered decrypting data", e);
        }
    }
}
