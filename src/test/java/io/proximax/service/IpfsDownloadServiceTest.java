package io.proximax.service;

import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
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
    public void failWhenNullDataHashList() {
        unitUnderTest.download(null);
    }

    @Test
    public void returnData() throws IOException {
        given(mockIpfsClient.getByteStream(SAMPLE_DATAHASH))
                .willReturn(Observable.just(new ByteArrayInputStream(SAMPLE_DATA)));

        final InputStream dataStream = unitUnderTest.download(SAMPLE_DATAHASH).blockingFirst();

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(dataStream)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }
}
