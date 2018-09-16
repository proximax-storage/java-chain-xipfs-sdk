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
import io.proximax.model.BlockchainNetworkType;
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
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestConfig.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestConfig.getIpfsMultiAddress())));
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

}
