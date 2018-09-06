package io.proximax.cipher;

import io.nem.core.crypto.Hashes;
import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.ed25519.Ed25519Utils;
import io.nem.core.crypto.ed25519.arithmetic.Ed25519EncodedGroupElement;
import io.nem.core.crypto.ed25519.arithmetic.Ed25519GroupElement;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * This factory class to creating cipher for password based encryptions
 */
public class BlockchainKeysCipherEncryptor {


	/**
	 * Encrypt byte stream with private and public keys
	 *
	 * @param byteStream the byte stream to encrypt
	 * @param privateKeyPair the key pair with private key
	 * @param publicKeyPair the key pair with public key
	 * @return the encrypted stream
	 */
	public InputStream encryptStream(InputStream byteStream, KeyPair privateKeyPair, KeyPair publicKeyPair) {
		try {
			byte[] salt = new byte[32];
			byte[] iv = new byte[16];
			SecureRandom rand = new SecureRandom();
			rand.nextBytes(salt);
			rand.nextBytes(iv);

			Cipher cipher = getCipherInstance();
			cipher.init(Cipher.ENCRYPT_MODE,
					getSharedKey(salt, privateKeyPair, publicKeyPair),
					getIvParameterSpec(iv));

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
	 * Decrypt byte stream with private and public keys
	 *
	 * @param byteStream the encrypted byte stream
	 * @param privateKeyPair the key pair with private key
	 * @param publicKeyPair the key pair with public key
	 * @return the decrypted stream
	 */
	public InputStream decryptStream(InputStream byteStream, KeyPair privateKeyPair, KeyPair publicKeyPair) {
		try {
			byte[] salt = new byte[32];
			byte[] iv = new byte[16];
			IOUtils.read(byteStream, salt);
			IOUtils.read(byteStream, iv);;

			Cipher cipher = getCipherInstance();
			cipher.init(Cipher.DECRYPT_MODE,
					getSharedKey(salt, privateKeyPair, publicKeyPair),
					getIvParameterSpec(iv));

			return new CipherInputStream(byteStream, cipher);
		} catch (Exception e) {
			throw new DecryptionFailureException("Failed to decrypt stream", e);
		}
	}

	private Cipher getCipherInstance() throws NoSuchAlgorithmException, NoSuchPaddingException {
		return Cipher.getInstance("AES/CBC/PKCS7Padding");
	}

	private IvParameterSpec getIvParameterSpec(byte[] iv) {
		return new IvParameterSpec(iv);
	}

	private static SecretKey getSharedKey(byte[] salt, KeyPair privateKeyPair, KeyPair publicKeyPair) {

		Ed25519GroupElement senderA = (new Ed25519EncodedGroupElement(publicKeyPair.getPublicKey().getRaw())).decode();
		senderA.precomputeForScalarMultiplication();
		byte[] sharedKey = senderA.scalarMultiply(Ed25519Utils.prepareForScalarMultiply(privateKeyPair.getPrivateKey())).encode().getRaw();

		for(int i = 0; i < 32; ++i) {
			sharedKey[i] ^= salt[i];
		}

		return new SecretKeySpec(Hashes.sha3_256(new byte[][]{sharedKey}), "AES");
	}

}
