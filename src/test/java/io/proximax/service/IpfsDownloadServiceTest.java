package io.proximax.service;

import io.proximax.model.StoreType;
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
        unitUnderTest.downloadList(null, StoreType.BLOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadListWhenNullStoreType() {
        unitUnderTest.downloadList(asList(SAMPLE_DATAHASH), null);
    }

    @Test
    public void returnDataListOnDownloadListWhenStoreTypeIsResource() {
        given(mockIpfsClient.get(SAMPLE_DATAHASH)).willReturn(Observable.just(SAMPLE_DATA));

        final List<byte[]> dataList = unitUnderTest.downloadList(asList(SAMPLE_DATAHASH), StoreType.RESOURCE).blockingFirst();

        assertThat(dataList, is(notNullValue()));
        assertThat(dataList, hasSize(1));
        assertThat(dataList.get(0), is(SAMPLE_DATA));
    }

    @Test
    public void returnDataListOnDownloadListWhenStoreTypeIsBlock() {
        given(mockIpfsClient.getBlock(SAMPLE_DATAHASH)).willReturn(Observable.just(SAMPLE_DATA));

        final List<byte[]> dataList = unitUnderTest.downloadList(asList(SAMPLE_DATAHASH), StoreType.BLOCK).blockingFirst();

        assertThat(dataList, is(notNullValue()));
        assertThat(dataList, hasSize(1));
        assertThat(dataList.get(0), is(SAMPLE_DATA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadWhenNullDataHashList() {
        unitUnderTest.download(null, StoreType.BLOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadWhenNullStoreType() {
        unitUnderTest.download(SAMPLE_DATAHASH, null);
    }

    @Test
    public void returnDataOnDownloadWhenStoreTypeIsResource() {
        given(mockIpfsClient.get(SAMPLE_DATAHASH)).willReturn(Observable.just(SAMPLE_DATA));

        final byte[] data = unitUnderTest.download(SAMPLE_DATAHASH, StoreType.RESOURCE).blockingFirst();

        assertThat(data, is(SAMPLE_DATA));
    }

    @Test
    public void returnDataOnDownloadWhenStoreTypeIsBlock() {
        given(mockIpfsClient.getBlock(SAMPLE_DATAHASH)).willReturn(Observable.just(SAMPLE_DATA));

        final byte[] data = unitUnderTest.download(SAMPLE_DATAHASH, StoreType.BLOCK).blockingFirst();

        assertThat(data, is(SAMPLE_DATA));
    }
}
