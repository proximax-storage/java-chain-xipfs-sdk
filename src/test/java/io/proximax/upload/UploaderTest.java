package io.proximax.upload;

import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.CreateProximaxDataService;
import io.proximax.service.CreateProximaxMessagePayloadService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UploaderTest {

    private Uploader unitUnderTest;

    @Mock
    private BlockchainTransactionService mockBlockchainTransactionService;

    @Mock
    private CreateProximaxDataService mockCreateProximaxDataService;

    @Mock
    private CreateProximaxMessagePayloadService mockCreateProximaxMessagePayloadService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new Uploader(mockBlockchainTransactionService, mockCreateProximaxDataService, mockCreateProximaxMessagePayloadService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadWhenNullUploadParameter() {
        unitUnderTest.upload(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadAsyncWhenNullUploadParameter() {
        unitUnderTest.uploadAsync(null, null);
    }

}
