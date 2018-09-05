package io.proximax.download;

import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.RetrieveProximaxDataService;
import io.proximax.service.RetrieveProximaxMessagePayloadService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DownloaderTest {

    private Downloader unitUnderTest;

    @Mock
    private BlockchainTransactionService mockBlockchainTransactionService;

    @Mock
    private RetrieveProximaxMessagePayloadService mockRetrieveProximaxMessagePayloadService;

    @Mock
    private RetrieveProximaxDataService mockRetrieveProximaxDataService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new Downloader(mockBlockchainTransactionService, mockRetrieveProximaxMessagePayloadService,
                mockRetrieveProximaxDataService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadWhenNullDownloadParameter() {
        unitUnderTest.download(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadAsyncWhenNullDownloadParameter() {
        unitUnderTest.downloadAsync(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDirectDownloadAsyncWhenNullDownloadDataParameter() {
        unitUnderTest.directDownload(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDirectDownloadWhenNullDownloadDataParameter() {
        unitUnderTest.directDownloadAsync(null, null);
    }

}
