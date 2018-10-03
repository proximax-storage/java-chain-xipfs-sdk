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

import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DirectDownloadFailureException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_asyncIntegrationTest {

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
    public void shouldDirectDownloadAsynchronouslyWithoutCallback() throws Exception {
        final String dataHash =
                TestDataRepository.getData("Uploader_integrationTest.shouldUploadByteArray", "dataHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromDataHash(dataHash).build();

        final AsyncTask asyncTask = unitUnderTest.directDownloadAsync(param, null);
        while (!asyncTask.isDone()) {
            Thread.sleep(50);
        }

        assertThat(asyncTask.isDone(), is(true));
    }

    @Test
    public void shouldDirectDownloadAsynchronouslyWithSuccessCallback() throws Exception {
        final String dataHash = TestDataRepository
                .getData("Uploader_integrationTest.shouldUploadByteArray", "dataHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromDataHash(dataHash).build();
        final CompletableFuture<InputStream> toPopulateOnSuccess = new CompletableFuture<>();

        unitUnderTest.directDownloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess::complete, null));
        final InputStream result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

        assertThat(result, is(notNullValue()));
        assertThat(IOUtils.toByteArray(result), is(notNullValue()));
    }

    @Test
    public void shouldDirectDownloadAsynchronouslyWithFailureCallback() throws Exception {
        final DirectDownloadParameter param = DirectDownloadParameter
                .createFromTransactionHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();
        final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

        unitUnderTest.directDownloadAsync(param, AsyncCallbacks.create(null, toPopulateOnFailure::complete));
        final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

        assertThat(throwable, instanceOf(DirectDownloadFailureException.class));

    }

    @Test
    public void shouldDirectDownloadMultipleRequestsAsynchronously() throws Exception {
        final String transactionHash = TestDataRepository
                .getData("Uploader_integrationTest.shouldUploadByteArray", "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash).build();
        final CompletableFuture<InputStream> toPopulateOnSuccess1 = new CompletableFuture<>();
        final CompletableFuture<InputStream> toPopulateOnSuccess2 = new CompletableFuture<>();
        final CompletableFuture<InputStream> toPopulateOnSuccess3 = new CompletableFuture<>();

        unitUnderTest.directDownloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess1::complete, null));
        unitUnderTest.directDownloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess2::complete, null));
        unitUnderTest.directDownloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess3::complete, null));
        final InputStream result1 = toPopulateOnSuccess1.get(5, TimeUnit.SECONDS);
        final InputStream result2 = toPopulateOnSuccess2.get(5, TimeUnit.SECONDS);
        final InputStream result3 = toPopulateOnSuccess3.get(5, TimeUnit.SECONDS);

        assertThat(result1, is(notNullValue()));
        assertThat(IOUtils.toByteArray(result1), is(notNullValue()));
        assertThat(result2, is(notNullValue()));
        assertThat(IOUtils.toByteArray(result2), is(notNullValue()));
        assertThat(result3, is(notNullValue()));
        assertThat(IOUtils.toByteArray(result3), is(notNullValue()));
    }
}
