package io.proximax.upload;

import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.CreateProximaxMessagePayloadService;
import io.proximax.service.CreateProximaxRootDataService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UploaderTest {

    private Uploader unitUnderTest;

    @Mock
    private BlockchainTransactionService mockBlockchainTransactionService;

    @Mock
    private CreateProximaxRootDataService mockCreateProximaxRootDataService;

    @Mock
    private CreateProximaxMessagePayloadService mockCreateProximaxMessagePayloadService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new Uploader(mockBlockchainTransactionService, mockCreateProximaxRootDataService, mockCreateProximaxMessagePayloadService);
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
