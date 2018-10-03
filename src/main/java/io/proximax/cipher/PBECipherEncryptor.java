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
package io.proximax.cipher;

import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * This factory class to creating cipher for password based encryptions
 */
public class PBECipherEncryptor {

    private static final String CONST_ALGO_PBKDF2 = "PBKDF2WithHmacSHA256";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Encrypt byte stream with password
     *
     * @param byteStream the byte stream to encrypt
     * @param password   the password
     * @return the encrypted stream
     */
    public InputStream encryptStream(InputStream byteStream, char[] password) {
        try {
            byte[] salt = new byte[32];
            byte[] iv = new byte[16];
            SecureRandom rand = new SecureRandom();
            rand.nextBytes(salt);
            rand.nextBytes(iv);

            Cipher cipher = getCipherInstance();
            cipher.init(Cipher.ENCRYPT_MODE,
                    getPBESecretKey(password, salt),
                    getGCMParameterSpec(iv));

            return new SequenceInputStream(Collections.enumeration(asList(
                    new ByteArrayInputStream(salt),
                    new ByteArrayInputStream(iv),
                    new CipherInputStream(byteStream, cipher)
            )));
        } catch (Exception e) {
            throw new EncryptionFailureException("Failed to encrypt stream", e);
        }
    }

    /**
     * Decrypt byte stream with password
     *
     * @param byteStream the encrypted byte stream
     * @param password   the password
     * @return the decrypted stream
     */
    public InputStream decryptStream(InputStream byteStream, char[] password) {
        try {
            byte[] salt = new byte[32];
            byte[] iv = new byte[16];
            IOUtils.read(byteStream, salt);
            IOUtils.read(byteStream, iv);

            Cipher cipher = getCipherInstance();
            cipher.init(Cipher.DECRYPT_MODE,
                    getPBESecretKey(password, salt),
                    getGCMParameterSpec(iv));

            return new CipherInputStream(byteStream, cipher);
        } catch (Exception e) {
            throw new DecryptionFailureException("Failed to decrypt stream", e);
        }
    }

    private SecretKey getPBESecretKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(CONST_ALGO_PBKDF2);
        KeySpec keyspec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(keyspec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    private Cipher getCipherInstance() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
        return Cipher.getInstance("AES/GCM/NoPadding", "BC");
    }

    private GCMParameterSpec getGCMParameterSpec(byte[] iv) {
        return new GCMParameterSpec(16 * 8, iv);
    }

}
