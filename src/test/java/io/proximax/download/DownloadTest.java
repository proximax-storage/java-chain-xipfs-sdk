package io.proximax.download;

import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.RetrieveProximaxDataService;
import io.proximax.service.RetrieveProximaxMessagePayloadService;
import io.proximax.service.RetrieveProximaxRootDataService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DownloadTest {

    private Download unitUnderTest;

    @Mock
    private BlockchainTransactionService mockBlockchainTransactionService;

    @Mock
    private RetrieveProximaxMessagePayloadService mockRetrieveProximaxMessagePayloadService;

    @Mock
    private RetrieveProximaxRootDataService mockRetrieveProximaxRootDataService;

    @Mock
    private RetrieveProximaxDataService mockRetrieveProximaxDataService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new Download(mockBlockchainTransactionService, mockRetrieveProximaxMessagePayloadService,
                mockRetrieveProximaxRootDataService, mockRetrieveProximaxDataService);
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
    public void failOnDownloadDataAsyncWhenNullDownloadDataParameter() {
        unitUnderTest.downloadData(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDownloadDataWhenNullDownloadDataParameter() {
        unitUnderTest.downloadDataAsync(null, null);
    }

}
