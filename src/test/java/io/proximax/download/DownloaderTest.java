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
