package io.proximax.privacy.strategy;

import io.nem.core.crypto.CryptoEngines;
import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.PrivacyType;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The privacy strategy that secures data using the NEM keys (a private key and a public key).
 * This strategy encrypt and decrypt the data using both private and public keys
 */
// TODO switch to secure message once available
public final class SecuredWithNemKeysPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    private final KeyPair keyPairOfPrivateKey;
    private final KeyPair keyPairOfPublicKey;

    SecuredWithNemKeysPrivacyStrategy(String privateKey, String publicKey, String searchTag) {
        super(searchTag);

        checkParameter(privateKey != null, "private key is required");
        checkParameter(publicKey != null, "public key is required");

        this.keyPairOfPrivateKey = new KeyPair(PrivateKey.fromHexString(privateKey));
        this.keyPairOfPublicKey = new KeyPair(PublicKey.fromHexString(publicKey));
    }

    /**
     * Get the privacy type which is set as NEMKEYS
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.NEMKEYS.getValue();
    }

    /**
     * Encrypt the data using the private and public keys provided
     * @param data data to encrypt
     * @return the encrypted data
     */
    @Override
    public final byte[] encryptData(byte[] data) {
        try {
            return CryptoEngines.defaultEngine().createBlockCipher(keyPairOfPrivateKey, keyPairOfPublicKey).encrypt(data);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered encrypting data", e);
        }
    }

    /**
     * Encrypt the data using the private and public keys provided
     * @param data encrypted data to decrypt
     * @return the decrypted data
     */
    @Override
    public final byte[] decryptData(byte[] data) {
        try {
            final byte[] decrypted = CryptoEngines.defaultEngine().createBlockCipher(keyPairOfPublicKey, keyPairOfPrivateKey).decrypt(data);
            if (decrypted == null) {
                throw new DecryptionFailureException("Exception encountered decrypting data");
            }
            return decrypted;
        } catch (Exception e) {
            throw new DecryptionFailureException("Exception encountered decrypting data", e);
        }
    }

    /**
     * Create instance of this strategy
     * @param privateKey the private key
     * @param publicKey the public key
     * @param searchTag an optional search tag
     * @return the instance of this strategy
     */
    public static SecuredWithNemKeysPrivacyStrategy create(String privateKey, String publicKey, String searchTag) {
        return new SecuredWithNemKeysPrivacyStrategy(privateKey, publicKey, searchTag);
    }
}
