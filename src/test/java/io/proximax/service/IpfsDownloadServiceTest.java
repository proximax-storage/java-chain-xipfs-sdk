package io.proximax.service;

import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;

public class IpfsDownloadServiceTest {
    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    public static final String SAMPLE_DATAHASH = "QmTxpkEitAczbM5S4uZG3zoDToSDNQZQUV4vxBsW9Q1Nhh";

    private IpfsDownloadService unitUnderTest;

    @Mock
    private IpfsClient mockIpfsClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new IpfsDownloadService(mockIpfsClient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadListWhenNullDataHashList() {
        unitUnderTest.downloadList(null);
    }

    @Test
    public void returnDataListOnDownloadLis() {
        given(mockIpfsClient.get(SAMPLE_DATAHASH)).willReturn(Observable.just(SAMPLE_DATA));

        final List<byte[]> dataList = unitUnderTest.downloadList(asList(SAMPLE_DATAHASH)).blockingFirst();

        assertThat(dataList, is(notNullValue()));
        assertThat(dataList, hasSize(1));
        assertThat(dataList.get(0), is(SAMPLE_DATA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadWhenNullDataHashList() {
        unitUnderTest.download(null);
    }

    @Test
    public void returnDataOnDownload() {
        given(mockIpfsClient.get(SAMPLE_DATAHASH)).willReturn(Observable.just(SAMPLE_DATA));

        final byte[] data = unitUnderTest.download(SAMPLE_DATAHASH).blockingFirst();

        assertThat(data, is(SAMPLE_DATA));
    }
}
