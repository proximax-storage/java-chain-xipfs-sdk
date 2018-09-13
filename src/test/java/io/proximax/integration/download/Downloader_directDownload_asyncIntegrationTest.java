package io.proximax.integration.download;

import io.proximax.async.AsyncCallback;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DirectDownloadFailureException;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.IntegrationTestProperties;
import io.proximax.testsupport.TestDataRepository;
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
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestProperties.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestProperties.getIpfsMultiAddress())));
	}

	@Test
	public void shouldDownloadDataAsynchronouslyWithoutCallback() throws Exception {
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
	public void shouldDownloadDataAsynchronouslyWithSuccessCallback() throws Exception {
		final String dataHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadByteArray", "dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash).build();
		final CompletableFuture<InputStream> toPopulateOnSuccess = new CompletableFuture<>();

		unitUnderTest.directDownloadAsync(param, AsyncCallback.create(toPopulateOnSuccess::complete, null));
		final InputStream result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

		assertThat(result, is(notNullValue()));
		assertThat(IOUtils.toByteArray(result), is(notNullValue()));
	}

	@Test
	public void shouldDownloadDataAsynchronouslyWithFailureCallback() throws Exception {
		final DirectDownloadParameter param = DirectDownloadParameter
				.createFromTransactionHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();
		final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

		unitUnderTest.directDownloadAsync(param, AsyncCallback.create(null, toPopulateOnFailure::complete));
		final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

		assertThat(throwable, instanceOf(DirectDownloadFailureException.class));

	}

}
