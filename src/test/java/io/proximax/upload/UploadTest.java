package io.proximax.upload;

import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.CreateProximaxMessagePayloadService;
import io.proximax.service.CreateProximaxRootDataService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UploadTest {

    private Upload unitUnderTest;

    @Mock
    private BlockchainTransactionService mockBlockchainTransactionService;

    @Mock
    private CreateProximaxRootDataService mockCreateProximaxRootDataService;

    @Mock
    private CreateProximaxMessagePayloadService mockCreateProximaxMessagePayloadService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new Upload(mockBlockchainTransactionService, mockCreateProximaxRootDataService, mockCreateProximaxMessagePayloadService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUploadParameter() {
        unitUnderTest.upload(null);
    }

}
