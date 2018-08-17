package io.proximax.utils;

import io.proximax.privacy.strategy.PrivacyStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class PrivacyDataEncryptionUtilsTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    public static final byte[] MOCK_ENCRYPTED_DATA = "encryptedData".getBytes();
    public static final byte[] SAMPLE_DATA_2 = "dsafafqweyqwi jknc,xhj syadyh asyhi yuiweyiuqwy ieqw".getBytes();
    public static final byte[] MOCK_ENCRYPTED_DATA_2 = "encryptedData2".getBytes();
    public static final List<byte[]> SAMPLE_DATA_LIST = asList(SAMPLE_DATA, SAMPLE_DATA_2);
    public static final List<byte[]> MOCK_ENCRYPTED_DATA_LIST = asList(MOCK_ENCRYPTED_DATA, MOCK_ENCRYPTED_DATA_2);

    private PrivacyDataEncryptionUtils unitUnderTest;

    @Mock
    private PrivacyStrategy mockPrivacyStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new PrivacyDataEncryptionUtils();
        given(mockPrivacyStrategy.encryptData(SAMPLE_DATA)).willReturn(MOCK_ENCRYPTED_DATA);
        given(mockPrivacyStrategy.encryptData(SAMPLE_DATA_2)).willReturn(MOCK_ENCRYPTED_DATA_2);
        given(mockPrivacyStrategy.decryptData(MOCK_ENCRYPTED_DATA)).willReturn(SAMPLE_DATA);
        given(mockPrivacyStrategy.decryptData(MOCK_ENCRYPTED_DATA_2)).willReturn(SAMPLE_DATA_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnEncryptWhenNullPrivacyStrategy() {
        unitUnderTest.encrypt(null, SAMPLE_DATA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnEncryptWhenNullData() {
        unitUnderTest.encrypt(mockPrivacyStrategy, null);
    }

    @Test
    public void shouldReturnEncryptedDataOnEncrypt() {
        final byte[] result = unitUnderTest.encrypt(mockPrivacyStrategy, SAMPLE_DATA).blockingFirst();

        assertThat(result, is(MOCK_ENCRYPTED_DATA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnEncryptListWhenNullPrivacyStrategy() {
        unitUnderTest.encryptList(null, SAMPLE_DATA_LIST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnEncryptListWhenNullDataList() {
        unitUnderTest.encryptList(mockPrivacyStrategy, null);
    }

    @Test
    public void shouldReturnEncryptedDataListOnEncryptList() {
        final List<byte[]> result = unitUnderTest.encryptList(mockPrivacyStrategy, SAMPLE_DATA_LIST).blockingFirst();

        assertThat(result, contains(MOCK_ENCRYPTED_DATA, MOCK_ENCRYPTED_DATA_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDecryptWhenNullPrivacyStrategy() {
        unitUnderTest.decrypt(null, MOCK_ENCRYPTED_DATA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDecryptWhenNullData() {
        unitUnderTest.decrypt(mockPrivacyStrategy, null);
    }

    @Test
    public void shouldReturnDecryptedDataOnDecrypt() {
        final byte[] result = unitUnderTest.decrypt(mockPrivacyStrategy, MOCK_ENCRYPTED_DATA).blockingFirst();

        assertThat(result, is(SAMPLE_DATA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDecryptListWhenNullPrivacyStrategy() {
        unitUnderTest.decryptList(null, MOCK_ENCRYPTED_DATA_LIST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDecryptListWhenNullData() {
        unitUnderTest.decryptList(mockPrivacyStrategy, null);
    }

    @Test
    public void shouldReturnDecryptedDataListOnDecryptList() {
        final List<byte[]> result = unitUnderTest.decryptList(mockPrivacyStrategy, MOCK_ENCRYPTED_DATA_LIST).blockingFirst();

        assertThat(result, contains(SAMPLE_DATA, SAMPLE_DATA_2));
    }
}
