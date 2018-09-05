package io.proximax.service;

import io.proximax.exceptions.DownloadForTypeNotSupportedException;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class RetrieveProximaxDataServiceTest {

    private static final String DUMMY_DATA_HASH = "Qmdahdksadjksahjk";
    private static final byte[] DUMMY_DOWNLOADED_DATA = "dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.".getBytes();
    private static final String DUMMY_DIGEST = "iowuqoieuqowueoiqw";
    private static final byte[] DUMMY_DECRYPTED_DATA = "dsajhjdhaskhdksahkdsaljkjlxnzcm,nxz".getBytes();

    private RetrieveProximaxDataService unitUnderTest;

    @Mock
    private IpfsDownloadService mockIpfsDownloadService;

    @Mock
    private DigestUtils mockDigestUtils;

    @Mock
    private PrivacyStrategy mockPrivacyStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new RetrieveProximaxDataService(mockIpfsDownloadService, mockDigestUtils);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullDataHash() {
        unitUnderTest.getData(null, mockPrivacyStrategy, true, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivacyStrategy() {
        unitUnderTest.getData(DUMMY_DATA_HASH, null, true, null, null);
    }

    @Test(expected = DownloadForTypeNotSupportedException.class)
    public void failWhenPathContentType() {
        unitUnderTest.getData(DUMMY_DATA_HASH, mockPrivacyStrategy, true, null, PATH_UPLOAD_CONTENT_TYPE);
    }

    @Test
    public void shouldReturnDownloadedData() {
        given(mockIpfsDownloadService.download(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA));
        given(mockDigestUtils.validateDigest(DUMMY_DOWNLOADED_DATA, DUMMY_DIGEST)).willReturn(Observable.just(true));
        given(mockPrivacyStrategy.decryptData(DUMMY_DOWNLOADED_DATA)).willReturn(DUMMY_DECRYPTED_DATA);

        final byte[] result =
                unitUnderTest.getData(DUMMY_DATA_HASH, mockPrivacyStrategy, true, DUMMY_DIGEST, "text/plain")
                        .blockingFirst();

        assertThat(result, is(DUMMY_DECRYPTED_DATA));
    }

    @Test(expected = RuntimeException.class)
    public void failWhenDigestsDoNotMatch() {
        given(mockIpfsDownloadService.download(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA));
        given(mockPrivacyStrategy.decryptData(DUMMY_DOWNLOADED_DATA)).willReturn(DUMMY_DECRYPTED_DATA);
        given(mockDigestUtils.validateDigest(DUMMY_DOWNLOADED_DATA, DUMMY_DIGEST)).willThrow(RuntimeException.class);

        final byte[] result =
                unitUnderTest.getData(DUMMY_DATA_HASH, mockPrivacyStrategy, true, DUMMY_DIGEST, "text/plain")
                        .blockingFirst();

        assertThat(result, is(DUMMY_DECRYPTED_DATA));
    }
}
