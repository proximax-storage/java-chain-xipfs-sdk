package io.proximax.privacy.strategy;

import io.proximax.cipher.BinaryPBKDF2CipherEncryption;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.PrivacyType;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class SecuredWithPasswordPrivacyStrategyTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    
    public static final String PASSWORD = "lkNzBmYmYyNTExZjZmNDYyZTdjYWJmNmY1MjJiYjFmZTk3Zjg2NDA5ZDlhOD";
    
    public static final String PASSWORD_TOO_SHORT = "too short for a password";

    @Mock
    private BinaryPBKDF2CipherEncryption encryptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCorrectPrivacyType() {
        final int privacyType = SecuredWithPasswordPrivacyStrategy.create(PASSWORD).getPrivacyType();

        assertThat(privacyType, is(PrivacyType.PASSWORD.getValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failInitWithoutPassword() {
        SecuredWithPasswordPrivacyStrategy.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failInitWithPasswordNotMeetingMinimumLength() {
        SecuredWithPasswordPrivacyStrategy.create(PASSWORD_TOO_SHORT);
    }

    @Test(expected = EncryptionFailureException.class)
    public void failOnExceptionWhileEncrypting() throws Exception {
        given(encryptor.encrypt(any(byte[].class), any(char[].class))).willThrow(new RuntimeException("failed encryption"));

        final SecuredWithPasswordPrivacyStrategy unitUnderTest = new SecuredWithPasswordPrivacyStrategy(encryptor, PASSWORD);
        unitUnderTest.encryptData(SAMPLE_DATA);
    }

    @Test
    public void shouldReturnEncryptedWithPassword() {
        final SecuredWithPasswordPrivacyStrategy unitUnderTest = SecuredWithPasswordPrivacyStrategy.create(PASSWORD);

        final byte[] encrypted = unitUnderTest.encryptData(SAMPLE_DATA);

        assertThat(ArrayUtils.toObject(encrypted), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = DecryptionFailureException.class)
    public void failOnExceptionWhileDecrypting() throws Exception {
        given(encryptor.decrypt(any(byte[].class), any(char[].class))).willThrow(new RuntimeException("failed encryption"));

        final SecuredWithPasswordPrivacyStrategy unitUnderTest = new SecuredWithPasswordPrivacyStrategy(encryptor, PASSWORD);
        unitUnderTest.decryptData(SAMPLE_DATA);
    }

    @Test
    public void shouldReturnDecryptedWithPassword() {
        final SecuredWithPasswordPrivacyStrategy unitUnderTest = SecuredWithPasswordPrivacyStrategy.create(PASSWORD);
        final byte[] encrypted = unitUnderTest.encryptData(SAMPLE_DATA);

        final byte[] decrypted = unitUnderTest.decryptData(encrypted);

        assertThat(ArrayUtils.toObject(decrypted), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

}
