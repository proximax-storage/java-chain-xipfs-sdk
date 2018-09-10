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
