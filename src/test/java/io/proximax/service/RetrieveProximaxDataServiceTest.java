package io.proximax.service;

import io.proximax.exceptions.DownloadForDataTypeNotSupportedException;
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

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class RetrieveProximaxDataServiceTest {

    private static final String DUMMY_DATA_HASH = "Qmdahdksadjksahjk";
    private static final InputStream DUMMY_DOWNLOADED_DATA_STREAM =
            new ByteArrayInputStream("dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.".getBytes());
    private static final String DUMMY_DIGEST = "iowuqoieuqowueoiqw";
    private static final InputStream DUMMY_DECRYPTED_DATA_STREAM =
            new ByteArrayInputStream("dsajhjdhaskhdksahkdsaljkjlxnzcm,nxz".getBytes());

    private RetrieveProximaxDataService unitUnderTest;

    @Mock
    private IpfsClient mockIpfsClient;

    @Mock
    private DigestUtils mockDigestUtils;

    @Mock
    private PrivacyStrategy mockPrivacyStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new RetrieveProximaxDataService(mockIpfsClient, mockDigestUtils);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullDataHash() {
        unitUnderTest.getDataByteStream(null, mockPrivacyStrategy, true, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivacyStrategy() {
        unitUnderTest.getDataByteStream(DUMMY_DATA_HASH, null, true, null, null);
    }

    @Test(expected = DownloadForDataTypeNotSupportedException.class)
    public void failWhenPathContentType() {
        unitUnderTest.getDataByteStream(DUMMY_DATA_HASH, mockPrivacyStrategy, true, null, PATH_UPLOAD_CONTENT_TYPE);
    }

    @Test
    public void shouldReturnDownloadedData() {
        given(mockIpfsClient.getByteStream(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA_STREAM));
        given(mockDigestUtils.validateDigest(DUMMY_DOWNLOADED_DATA_STREAM, DUMMY_DIGEST)).willReturn(Observable.just(true));
        given(mockPrivacyStrategy.decryptStream(DUMMY_DOWNLOADED_DATA_STREAM)).willReturn(DUMMY_DECRYPTED_DATA_STREAM);

        final InputStream result =
                unitUnderTest.getDataByteStream(DUMMY_DATA_HASH, mockPrivacyStrategy, true, DUMMY_DIGEST, "text/plain")
                        .blockingFirst();

        assertThat(result, is(DUMMY_DECRYPTED_DATA_STREAM));
    }

    @Test(expected = RuntimeException.class)
    public void failWhenDigestsDoNotMatch() {
        given(mockIpfsClient.getByteStream(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA_STREAM));
        given(mockPrivacyStrategy.decryptStream(DUMMY_DOWNLOADED_DATA_STREAM)).willReturn(DUMMY_DECRYPTED_DATA_STREAM);
        given(mockDigestUtils.validateDigest(DUMMY_DOWNLOADED_DATA_STREAM, DUMMY_DIGEST)).willThrow(RuntimeException.class);

        final InputStream result =
                unitUnderTest.getDataByteStream(DUMMY_DATA_HASH, mockPrivacyStrategy, true, DUMMY_DIGEST, "text/plain")
                        .blockingFirst();

        assertThat(result, is(DUMMY_DECRYPTED_DATA_STREAM));
    }
}
