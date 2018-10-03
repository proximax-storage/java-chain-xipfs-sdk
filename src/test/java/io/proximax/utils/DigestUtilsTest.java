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
package io.proximax.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DigestUtilsTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    public static final String SAMPLE_DIGEST_HEX = "05c6e08f1d9fdafa03147fcb8f82f124c76d2f70e3d989dc8aadb5e7d7450bec";

    private DigestUtils unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new DigestUtils();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDigestWhenNullData() {
        unitUnderTest.digest(null);
    }

    @Test
    public void shouldReturnDigestOnDigest() {
        final String result = unitUnderTest.digest(new ByteArrayInputStream(SAMPLE_DATA)).blockingFirst();

        assertThat(result, is(SAMPLE_DIGEST_HEX));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnValidateDigestWhenNullData() {
        unitUnderTest.validateDigest(null, null);
    }

    @Test
    public void shouldReturnTrueOnValidateDigestWhenNullExpectedDigest() {
        final Boolean result = unitUnderTest.validateDigest(new ByteArrayInputStream(SAMPLE_DATA), null).blockingFirst();

        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnTrueOnValidateDigestWhenExpectedDigestMatches() {
        final Boolean result = unitUnderTest.validateDigest(new ByteArrayInputStream(SAMPLE_DATA), SAMPLE_DIGEST_HEX).blockingFirst();

        assertThat(result, is(true));
    }
}
