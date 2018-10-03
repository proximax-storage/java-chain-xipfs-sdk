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
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_download_asyncIntegrationTest {

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
    public void shouldDownloadAsynchronouslyWithoutCallback() throws Exception {
        final String transactionHash = TestDataRepository
                .getData("Uploader_integrationTest.shouldUploadByteArray", "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .build();

        final AsyncTask asyncTask = unitUnderTest.downloadAsync(param, null);
        while (!asyncTask.isDone()) {
            Thread.sleep(50);
        }

        assertThat(asyncTask.isDone(), is(true));
    }

    @Test
    public void shouldDownloadAsynchronouslyWithSuccessCallback() throws Exception {
        final String transactionHash = TestDataRepository
                .getData("Uploader_integrationTest.shouldUploadByteArray", "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .build();
        final CompletableFuture<DownloadResult> toPopulateOnSuccess = new CompletableFuture<>();

        unitUnderTest.downloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess::complete, null));
        final DownloadResult result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

        assertThat(result, is(notNullValue()));
        assertThat(result.getData(), is(notNullValue()));
        assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
    }

    @Test
    public void shouldDownloadAsynchronouslyWithFailureCallback() throws Exception {
        final DownloadParameter param = DownloadParameter
                .create("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();
        final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

        unitUnderTest.downloadAsync(param, AsyncCallbacks.create(null, toPopulateOnFailure::complete));
        final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

        assertThat(throwable, instanceOf(DownloadFailureException.class));
    }

    @Test
    public void shouldDownloadMultipleRequestsAsynchronously() throws Exception {
        final String transactionHash = TestDataRepository
                .getData("Uploader_integrationTest.shouldUploadByteArray", "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .build();
        final CompletableFuture<DownloadResult> toPopulateOnSuccess1 = new CompletableFuture<>();
        final CompletableFuture<DownloadResult> toPopulateOnSuccess2 = new CompletableFuture<>();
        final CompletableFuture<DownloadResult> toPopulateOnSuccess3 = new CompletableFuture<>();

        unitUnderTest.downloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess1::complete, null));
        unitUnderTest.downloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess2::complete, null));
        unitUnderTest.downloadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess3::complete, null));
        final DownloadResult result1 = toPopulateOnSuccess1.get(5, TimeUnit.SECONDS);
        final DownloadResult result2 = toPopulateOnSuccess2.get(5, TimeUnit.SECONDS);
        final DownloadResult result3 = toPopulateOnSuccess3.get(5, TimeUnit.SECONDS);

        assertThat(result1, is(notNullValue()));
        assertThat(result1.getData(), is(notNullValue()));
        assertThat(IOUtils.toByteArray(result1.getData().getByteStream()), is(notNullValue()));
        assertThat(result2, is(notNullValue()));
        assertThat(result2.getData(), is(notNullValue()));
        assertThat(IOUtils.toByteArray(result2.getData().getByteStream()), is(notNullValue()));
        assertThat(result3, is(notNullValue()));
        assertThat(result3.getData(), is(notNullValue()));
        assertThat(IOUtils.toByteArray(result3.getData().getByteStream()), is(notNullValue()));
    }
}
