package io.proximax.cipher;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class BinaryPBKDF2CipherEncryptionTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    public static final char[] PASSWORD = "lkNzBmYmYyNTExZjZmNDYyZTdjYWJmNmY1MjJiYjFmZTk3Zjg2NDA5ZDlhOD".toCharArray();

    private BinaryPBKDF2CipherEncryption unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new BinaryPBKDF2CipherEncryption();
    }

    @Test
    public void shouldReturnEncryptedDataOnEncrypt() throws NoSuchPaddingException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        final byte[] encrypted = unitUnderTest.encrypt(SAMPLE_DATA, PASSWORD);

        assertThat(ArrayUtils.toObject(encrypted), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void shouldReturnDecryptedDataOnDecrypt() throws NoSuchPaddingException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        final byte[] encrypted = unitUnderTest.encrypt(SAMPLE_DATA, PASSWORD);

        final byte[] decrypted = unitUnderTest.decrypt(encrypted, PASSWORD);

        assertThat(ArrayUtils.toObject(decrypted), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));

    }
}
