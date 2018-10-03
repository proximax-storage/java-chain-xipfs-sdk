/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
