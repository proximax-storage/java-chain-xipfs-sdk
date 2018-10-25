package io.proximax.service;

import io.proximax.model.PrivacyType;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.client.IpfsClient;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Supplier;

import static io.proximax.testsupport.Constants.TEST_PATH_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;

public class FileUploadServiceTest {
    private static final InputStream SAMPLE_DATA_STREAM = new ByteArrayInputStream("dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.".getBytes());
    private static final Supplier<InputStream> SAMPLE_DATA_STREAM_SUPPLIER = () -> SAMPLE_DATA_STREAM;
    private static final String SAMPLE_DATAHASH = "QmTxpkEitAczbM5S4uZG3zoDToSDNQZQUV4vxBsW9Q1Nhh";
    private static final InputStream SAMPLE_ENCRYPTED_DATA_STREAM = new ByteArrayInputStream("dsajhjdhaskhdksahkdsaljkjlxnzcm,nxz".getBytes());
    private static final String SAMPLE_DIGEST = "Qmdsewquywqiyeiuqwyiueyqiuyeuiwyqid";

    private FileUploadService unitUnderTest;

    @Mock
    private IpfsClient mockIpfsClient;

    @Mock
    private DigestUtils mockDigestUtils;

    @Mock
    private PrivacyStrategy mockPrivacyStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new FileUploadService(mockIpfsClient, mockDigestUtils);

        given(mockPrivacyStrategy.getPrivacyType()).willReturn(PrivacyType.PLAIN.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnAddByteStreamWhenNullByteStreamSupplier() {
        unitUnderTest.uploadByteStream(null, mockPrivacyStrategy, false);
    }

    @Test
    public void shouldAddByteStream() {
        given(mockIpfsClient.addByteStream(SAMPLE_DATA_STREAM))
                .willReturn(Observable.just(SAMPLE_DATAHASH));

        final FileUploadResponse ipfsUploadResponse =
                unitUnderTest.uploadByteStream(SAMPLE_DATA_STREAM_SUPPLIER, null, false).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
        assertThat(ipfsUploadResponse.getDigest(), is(nullValue()));
    }

    @Test
    public void shouldAddByteStreamWithPrivacyStrategy() {
        given(mockIpfsClient.addByteStream(SAMPLE_ENCRYPTED_DATA_STREAM))
                .willReturn(Observable.just(SAMPLE_DATAHASH));
        given(mockPrivacyStrategy.encryptStream(SAMPLE_DATA_STREAM))
                .willReturn(SAMPLE_ENCRYPTED_DATA_STREAM);

        final FileUploadResponse ipfsUploadResponse =
                unitUnderTest.uploadByteStream(SAMPLE_DATA_STREAM_SUPPLIER, mockPrivacyStrategy, false).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
        assertThat(ipfsUploadResponse.getDigest(), is(nullValue()));
    }

    @Test
    public void shouldAddByteStreamWithComputeDigest() {
        given(mockIpfsClient.addByteStream(SAMPLE_DATA_STREAM))
                .willReturn(Observable.just(SAMPLE_DATAHASH));
        given(mockDigestUtils.digest(SAMPLE_DATA_STREAM))
                .willReturn(Observable.just(SAMPLE_DIGEST));

        final FileUploadResponse ipfsUploadResponse =
                unitUnderTest.uploadByteStream(SAMPLE_DATA_STREAM_SUPPLIER, null, true).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
        assertThat(ipfsUploadResponse.getDigest(), is(SAMPLE_DIGEST));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadPathWhenNullPath() {
        unitUnderTest.uploadPath(null);
    }

    @Test
    public void shouldReturnDataHashOnUploadPath() {
        given(mockIpfsClient.addPath(TEST_PATH_FILE)).willReturn(Observable.just(SAMPLE_DATAHASH));

        final FileUploadResponse ipfsUploadResponse =
                unitUnderTest.uploadPath(TEST_PATH_FILE).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
    }
}
