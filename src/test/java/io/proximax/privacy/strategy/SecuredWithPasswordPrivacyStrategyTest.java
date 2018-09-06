package io.proximax.privacy.strategy;

import io.proximax.cipher.PBECipherEncryptor;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.exceptions.EncryptionFailureException;
import io.proximax.model.PrivacyType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class SecuredWithPasswordPrivacyStrategyTest {

    private static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    private static final String PASSWORD = "lkNzBmYmYyNTExZjZmNDYyZTdjYWJmNmY1MjJiYjFmZTk3Zjg2NDA5ZDlhOD";

    private static final String PASSWORD_TOO_SHORT = "too short for a password";

    @Mock
    private PBECipherEncryptor encryptor;

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
        given(encryptor.encryptStream(any(), any())).willThrow(new EncryptionFailureException("failed encryption"));

        final SecuredWithPasswordPrivacyStrategy unitUnderTest = new SecuredWithPasswordPrivacyStrategy(encryptor, PASSWORD);
        unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));
    }

    @Test
    public void shouldReturnEncryptedWithPassword() throws IOException {
        final SecuredWithPasswordPrivacyStrategy unitUnderTest = SecuredWithPasswordPrivacyStrategy.create(PASSWORD);

        final InputStream encrypted = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(encrypted)), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = DecryptionFailureException.class)
    public void failOnExceptionWhileDecrypting() throws Exception {
        given(encryptor.decryptStream(any(), any())).willThrow(new DecryptionFailureException("failed encryption"));

        final SecuredWithPasswordPrivacyStrategy unitUnderTest = new SecuredWithPasswordPrivacyStrategy(encryptor, PASSWORD);
        unitUnderTest.decryptStream(new ByteArrayInputStream(SAMPLE_DATA));
    }

    @Test
    public void shouldReturnDecryptedWithPassword() throws IOException {
        final SecuredWithPasswordPrivacyStrategy unitUnderTest = SecuredWithPasswordPrivacyStrategy.create(PASSWORD);
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream);

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

}
