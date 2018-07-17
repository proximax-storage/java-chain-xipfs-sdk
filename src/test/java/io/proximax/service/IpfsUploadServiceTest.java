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
        unitUnderTest.uploadList(null, StoreType.BLOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadListWhenNullStoreType() {
        unitUnderTest.uploadList(asList(SAMPLE_DATA), null);
    }

    @Test
    public void shouldReturnDataHashListOnUploadListWhenStoreTypeIsResource() {
        given(mockIpfsClient.add(SAMPLE_DATA)).willReturn(Observable.just(SAMPLE_DATAHASH));

        final List<IpfsUploadResponse> ipfsUploadResponses =
                unitUnderTest.uploadList(asList(SAMPLE_DATA), StoreType.RESOURCE).blockingFirst();

        assertThat(ipfsUploadResponses, is(notNullValue()));
        assertThat(ipfsUploadResponses, hasSize(1));
        assertThat(ipfsUploadResponses.get(0).getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponses.get(0).getTimestamp(), is(notNullValue()));
    }

    @Test
    public void shouldReturnDataHashListOnUploadListWhenStoreTypeIsBlock() {
        given(mockIpfsClient.addBlock(SAMPLE_DATA)).willReturn(Observable.just(SAMPLE_DATAHASH));
        given(mockIpfsClient.pin(SAMPLE_DATAHASH)).willReturn(Observable.just(asList(SAMPLE_DATAHASH)));

        final List<IpfsUploadResponse> ipfsUploadResponses =
                unitUnderTest.uploadList(asList(SAMPLE_DATA), StoreType.BLOCK).blockingFirst();

        assertThat(ipfsUploadResponses, is(notNullValue()));
        assertThat(ipfsUploadResponses, hasSize(1));
        assertThat(ipfsUploadResponses.get(0).getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponses.get(0).getTimestamp(), is(notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadWhenNullDataList() {
        unitUnderTest.upload(null, StoreType.BLOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadWhenNullStoreType() {
        unitUnderTest.upload(SAMPLE_DATA, null);
    }

    @Test
    public void shouldReturnDataHashOnUploadWhenStoreTypeIsResource() {
        given(mockIpfsClient.add(SAMPLE_DATA)).willReturn(Observable.just(SAMPLE_DATAHASH));

        final IpfsUploadResponse ipfsUploadResponse =
                unitUnderTest.upload(SAMPLE_DATA, StoreType.RESOURCE).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
    }

    @Test
    public void shouldReturnDataHashOnUploadWhenStoreTypeIsBlock() {
        given(mockIpfsClient.addBlock(SAMPLE_DATA)).willReturn(Observable.just(SAMPLE_DATAHASH));
        given(mockIpfsClient.pin(SAMPLE_DATAHASH)).willReturn(Observable.just(asList(SAMPLE_DATAHASH)));

        final IpfsUploadResponse ipfsUploadResponse =
                unitUnderTest.upload(SAMPLE_DATA, StoreType.BLOCK).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
    }

}
