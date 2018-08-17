package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class PlainPrivacyStrategyTest {

    public static final byte[] INPUT_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    private PlainPrivacyStrategy unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new PlainPrivacyStrategy("test");
    }

    @Test
    public void shouldReturnCorrectPrivacyType() {
        final int privacyType = unitUnderTest.getPrivacyType();

        assertThat(privacyType, is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void shouldReturnSameDataOnEncrypt() {
        final byte[] encrypted = unitUnderTest.encryptData(INPUT_DATA);

        assertThat(ArrayUtils.toObject(encrypted), is(arrayContaining(ArrayUtils.toObject(INPUT_DATA))));
    }

    @Test
    public void shouldReturnSameDataOnDecrypt() {
        final byte[] decrypted = unitUnderTest.decryptData(INPUT_DATA);

        assertThat(ArrayUtils.toObject(decrypted), is(arrayContaining(ArrayUtils.toObject(INPUT_DATA))));
    }
}
