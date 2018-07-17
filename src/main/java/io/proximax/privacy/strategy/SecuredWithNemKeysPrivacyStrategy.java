package io.proximax.privacy.strategy;

import io.nem.core.crypto.CryptoEngines;
import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.PrivacyType;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

// TODO switch to secure message once available
public final class SecuredWithNemKeysPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    public final KeyPair keyPairOfPrivateKey;
    public final KeyPair keyPairOfPublicKey;

    public SecuredWithNemKeysPrivacyStrategy(String privateKey, String publicKey, String searchTag) {
        super(searchTag);

        checkParameter(privateKey != null, "private key is required");
        checkParameter(publicKey != null, "public key is required");

        this.keyPairOfPrivateKey = new KeyPair(PrivateKey.fromHexString(privateKey));
        this.keyPairOfPublicKey = new KeyPair(PublicKey.fromHexString(publicKey));
    }

    @Override
    public int getPrivacyType() {
        return PrivacyType.NEMKEYS.getValue();
    }

    @Override
    public final byte[] encryptData(byte[] data) {
        try {
            return CryptoEngines.defaultEngine().createBlockCipher(keyPairOfPrivateKey, keyPairOfPublicKey).encrypt(data);
        } catch (Exception e) {
            throw new EncryptionFailureException("Exception encountered encrypting data", e);
        }
    }

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

    public static SecuredWithNemKeysPrivacyStrategy create(String privateKey, String publicKey, String searchTag) {
        return new SecuredWithNemKeysPrivacyStrategy(privateKey, publicKey, searchTag);
    }
}
