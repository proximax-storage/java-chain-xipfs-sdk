package io.proximax.integration.download;

import io.proximax.async.AsyncCallback;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.Downloader;
import io.proximax.download.DownloadDataParameter;
import io.proximax.download.DownloadDataResult;
import io.proximax.exceptions.DownloadDataFailureException;
import io.proximax.model.BlockchainNetwork;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_downloadData_asyncIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetwork.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadDataAsynchronouslyWithoutCallback() throws Exception {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadAllDataTypes", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash).build();

		final AsyncTask asyncTask = unitUnderTest.downloadDataAsync(param, null);
		while (!asyncTask.isDone()) {
			Thread.sleep(50);
		}

		assertThat(asyncTask.isDone(), is(true));
	}

	@Test
	public void shouldDownloadDataAsynchronouslyWithSuccessCallback() throws Exception {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadAllDataTypes", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash).build();
		final CompletableFuture<DownloadDataResult> toPopulateOnSuccess = new CompletableFuture<>();

		unitUnderTest.downloadDataAsync(param, AsyncCallback.create(toPopulateOnSuccess::complete, null));
		final DownloadDataResult result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

		assertThat(result, is(notNullValue()));
		assertThat(result.getData(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadDataAsynchronouslyWithFailureCallback() throws Exception {
		final DownloadDataParameter param =
				DownloadDataParameter.create("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();
		final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

		unitUnderTest.downloadDataAsync(param, AsyncCallback.create(null, toPopulateOnFailure::complete));
		final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

		assertThat(throwable, instanceOf(DownloadDataFailureException.class));

	}

}
