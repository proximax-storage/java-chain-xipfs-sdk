package io.proximax.utils;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DigestUtilsTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    public static final String SAMPLE_DIGEST_HEX = "05c6e08f1d9fdafa03147fcb8f82f124c76d2f70e3d989dc8aadb5e7d7450bec";
    public static final byte[] SAMPLE_DATA_2 = "lorem ipsum sum rem hjdsajk iydsaihkkn ewqyui".getBytes();
    public static final String SAMPLE_DIGEST_HEX_2 = "df8ddd5207ed029a024c93b8b60e53c6c5054a31cda8353431b6c4c23f8bdf41";

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
        final String result = unitUnderTest.digest(SAMPLE_DATA).blockingFirst();

        assertThat(result, is(SAMPLE_DIGEST_HEX));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnValidateDigestWhenNullData() {
        unitUnderTest.validateDigest(null, null);
    }

    @Test
    public void shouldReturnTrueOnValidateDigestWhenNullExpectedDigest() {
        final Boolean result = unitUnderTest.validateDigest(SAMPLE_DATA, null).blockingFirst();

        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnTrueOnValidateDigestWhenExpectedDigestMatches() {
        final Boolean result = unitUnderTest.validateDigest(SAMPLE_DATA, SAMPLE_DIGEST_HEX).blockingFirst();

        assertThat(result, is(true));
    }
}
