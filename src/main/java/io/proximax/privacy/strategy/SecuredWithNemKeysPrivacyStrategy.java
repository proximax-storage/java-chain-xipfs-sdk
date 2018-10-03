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

import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.proximax.cipher.BlockchainKeysCipherEncryptor;
import io.proximax.model.PrivacyType;

import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The privacy strategy that secures data using the NEM keys (a private key and a public key).
 * This strategy encrypt and decrypt the data using both private and public keys
 */
public final class SecuredWithNemKeysPrivacyStrategy extends PrivacyStrategy {

    private final BlockchainKeysCipherEncryptor blockchainKeysCipherEncryptor;
    private final KeyPair keyPairOfPrivateKey;
    private final KeyPair keyPairOfPublicKey;

    SecuredWithNemKeysPrivacyStrategy(BlockchainKeysCipherEncryptor blockchainKeysCipherEncryptor,
                                      String privateKey, String publicKey) {

        checkParameter(privateKey != null, "private key is required");
        checkParameter(publicKey != null, "public key is required");

        this.blockchainKeysCipherEncryptor = blockchainKeysCipherEncryptor;
        this.keyPairOfPrivateKey = new KeyPair(PrivateKey.fromHexString(privateKey));
        this.keyPairOfPublicKey = new KeyPair(PublicKey.fromHexString(publicKey));
    }

    /**
     * Get the privacy type which is set as NEMKEYS
     *
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.NEMKEYS.getValue();
    }

    /**
     * Encrypt byte stream using the private and public keys provided
     *
     * @param byteStream the byte stream to encrypt
     * @return the encrypted byte stream
     */
    @Override
    public final InputStream encryptStream(final InputStream byteStream) {
        return blockchainKeysCipherEncryptor.encryptStream(byteStream, keyPairOfPrivateKey, keyPairOfPublicKey);
    }

    /**
     * Encrypt byte stream using the private and public keys provided
     *
     * @param byteStream the byte stream to decrypt
     * @return the decrypted byte stream
     */
    @Override
    public final InputStream decryptStream(final InputStream byteStream) {
        return blockchainKeysCipherEncryptor.decryptStream(byteStream, keyPairOfPrivateKey, keyPairOfPublicKey);
    }

    /**
     * Create instance of this strategy
     *
     * @param privateKey the private key
     * @param publicKey  the public key
     * @return the instance of this strategy
     */
    public static SecuredWithNemKeysPrivacyStrategy create(String privateKey, String publicKey) {
        return new SecuredWithNemKeysPrivacyStrategy(new BlockchainKeysCipherEncryptor(), privateKey, publicKey);
    }
}
