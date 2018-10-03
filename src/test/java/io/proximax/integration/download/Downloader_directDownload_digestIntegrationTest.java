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
package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.TEST_STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_digestIntegrationTest {

    private Downloader unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new Downloader(ConnectionConfig.createWithLocalIpfsConnection(
                new BlockchainNetworkConnection(
                        IntegrationTestConfig.getBlockchainNetworkType(),
                        IntegrationTestConfig.getBlockchainApiHost(),
                        IntegrationTestConfig.getBlockchainApiPort(),
                        IntegrationTestConfig.getBlockchainApiProtocol()),
                new IpfsConnection(
                        IntegrationTestConfig.getIpfsApiHost(),
                        IntegrationTestConfig.getIpfsApiPort())));
    }

    @Test
    public void shouldVerifyDirectDownloadUsingTransactionHashWithEnabledValidateDigest() throws IOException {
        final String transactionHash = TestDataRepository
                .getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash, true).build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
    }

    @Test
    public void shouldNotVerifyDirectDownloadUsingTransactionHashWithDisabledValidateDigest() throws IOException {
        final String transactionHash = TestDataRepository
                .getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash, false).build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
    }

    @Test
    public void shouldVerifyDirectDownloadUsingDataHashAndDigest() throws IOException {
        final String dataHash = TestDataRepository
                .getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "dataHash");
        final String digest = TestDataRepository
                .getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "digest");
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash, digest).build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
    }

    @Test
    public void shouldNotVerifyDirectDownloadUsingDataHashOnly() throws IOException {
        final String dataHash = TestDataRepository
                .getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "dataHash");
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
    }
}
