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

import io.proximax.model.PrivacyType;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PlainPrivacyStrategyTest {

    private static final InputStream INPUT_DATA_STREAM =
            new ByteArrayInputStream("the quick brown fox jumps over the lazy dog".getBytes());

    private PlainPrivacyStrategy unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = PlainPrivacyStrategy.create();
    }

    @Test
    public void shouldReturnCorrectPrivacyType() {
        final int privacyType = unitUnderTest.getPrivacyType();

        assertThat(privacyType, is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void shouldReturnSameDataOnEncrypt() {
        final InputStream encrypted = unitUnderTest.encryptStream(INPUT_DATA_STREAM);

        assertThat(encrypted, is(INPUT_DATA_STREAM));
    }

    @Test
    public void shouldReturnSameDataOnDecrypt() {
        final InputStream decrypted = unitUnderTest.decryptStream(INPUT_DATA_STREAM);

        assertThat(decrypted, is(INPUT_DATA_STREAM));
    }
}
