package io.proximax.utils;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DigestUtilsTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    public static final String SAMPLE_DIGEST_HEX = "05c6e08f1d9fdafa03147fcb8f82f124c76d2f70e3d989dc8aadb5e7d7450bec";

    @Test(expected = IllegalArgumentException.class)
    public void failOnDigestWhenNullData() {
        DigestUtils.digest(null);
    }

    @Test
    public void shouldReturnDigestOnDigest() {
        final String result = DigestUtils.digest(new ByteArrayInputStream(SAMPLE_DATA));

        assertThat(result, is(SAMPLE_DIGEST_HEX));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnValidateDigestWhenNullData() {
        DigestUtils.validateDigest(null, null);
    }

    @Test
    public void shouldReturnTrueOnValidateDigestWhenNullExpectedDigest() {
        final Boolean result = DigestUtils.validateDigest(new ByteArrayInputStream(SAMPLE_DATA), null);

        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnTrueOnValidateDigestWhenExpectedDigestMatches() {
        final Boolean result = DigestUtils.validateDigest(new ByteArrayInputStream(SAMPLE_DATA), SAMPLE_DIGEST_HEX);

        assertThat(result, is(true));
    }
}
