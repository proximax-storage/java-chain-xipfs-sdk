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

public class IpfsUploadServiceTest {
    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    public static final String SAMPLE_DATAHASH = "QmTxpkEitAczbM5S4uZG3zoDToSDNQZQUV4vxBsW9Q1Nhh";

    private IpfsUploadService unitUnderTest;

    @Mock
    private IpfsClient mockIpfsClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new IpfsUploadService(mockIpfsClient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadListWhenNullDataList() {
        unitUnderTest.uploadList(null);
    }

    @Test
    public void shouldReturnDataHashListOnUploadList() {
        given(mockIpfsClient.add(SAMPLE_DATA)).willReturn(Observable.just(SAMPLE_DATAHASH));

        final List<IpfsUploadResponse> ipfsUploadResponses =
                unitUnderTest.uploadList(asList(SAMPLE_DATA)).blockingFirst();

        assertThat(ipfsUploadResponses, is(notNullValue()));
        assertThat(ipfsUploadResponses, hasSize(1));
        assertThat(ipfsUploadResponses.get(0).getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponses.get(0).getTimestamp(), is(notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadWhenNullDataList() {
        unitUnderTest.upload(null);
    }

    @Test
    public void shouldReturnDataHashOnUpload() {
        given(mockIpfsClient.add(SAMPLE_DATA)).willReturn(Observable.just(SAMPLE_DATAHASH));

        final IpfsUploadResponse ipfsUploadResponse =
                unitUnderTest.upload(SAMPLE_DATA).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
    }
}
