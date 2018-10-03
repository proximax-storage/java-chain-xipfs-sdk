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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.TEST_PDF_FILE_1;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class PBECipherEncryptorTest {

    public static final byte[] SAMPLE_DATA = getSampleBytes();

    public static final char[] PASSWORD = "lkNzBmYmYyNTExZjZmNDYyZTdjYWJmNmY1MjJiYjFmZTk3Zjg2NDA5ZDlhOD".toCharArray();

    private PBECipherEncryptor unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new PBECipherEncryptor();
    }

    @Test
    public void shouldReturnEncryptedDataOnEncrypt() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA), PASSWORD);

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(encryptedStream)), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void shouldReturnDecryptedDataOnDecrypt() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA), PASSWORD);

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream, PASSWORD);

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = IOException.class)
    public void failOnDecryptWhenIncorrectPassword() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA), PASSWORD);

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream, "password".toCharArray());

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    private static byte[] getSampleBytes() {
        try {
            return FileUtils.readFileToByteArray(TEST_PDF_FILE_1);
        } catch (IOException e) {
            return null;
        }
    }
}
