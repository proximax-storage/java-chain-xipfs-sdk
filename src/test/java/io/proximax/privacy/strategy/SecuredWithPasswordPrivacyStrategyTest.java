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

import static io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy.MINIMUM_PASSWORD_LENGTH;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class SecuredWithPasswordPrivacyStrategyTest {

    private static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    private static final String PASSWORD = "lkNzBmYmYyNTExZjZmNDYyZTdjYWJmNmY1MjJiYjFmZTk3Zjg2NDA5ZDlhOD";

    private static final String PASSWORD_TOO_SHORT = "too short";

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

    @Test
    public void shouldCreateWithProvidedPassword() {
        final SecuredWithPasswordPrivacyStrategy strategy = SecuredWithPasswordPrivacyStrategy.create(PASSWORD);

        assertThat(strategy, is(notNullValue()));
        assertThat(strategy.getPassword(), is(PASSWORD));
    }

    @Test
    public void shouldCreateWithGeneratedPassword() {
        final SecuredWithPasswordPrivacyStrategy strategy = SecuredWithPasswordPrivacyStrategy.create();

        assertThat(strategy, is(notNullValue()));
        assertThat(strategy.getPassword(), is(notNullValue()));
        assertThat(strategy.getPassword().length(), is(MINIMUM_PASSWORD_LENGTH));
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
