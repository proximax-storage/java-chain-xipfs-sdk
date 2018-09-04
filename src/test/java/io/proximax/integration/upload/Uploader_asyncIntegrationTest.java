package io.proximax.integration.upload;

import io.proximax.async.AsyncCallback;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.UploadFailureException;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PDF_FILE1;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.SMALL_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_asyncIntegrationTest {

	private Uploader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Uploader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldUploadAsynchronouslyWithoutCallback() throws Exception {
		final UploadParameter param = UploadParameter.createForByteArrayUpload(
				FileUtils.readFileToByteArray(PDF_FILE1), PRIVATE_KEY_1)
				.build();

		final AsyncTask asyncTask = unitUnderTest.uploadAsync(param, null);
		while (!asyncTask.isDone()) {
			Thread.sleep(50);
		}

		assertThat(asyncTask.isDone(), is(true));
	}

	@Test
	public void shouldUploadAsynchronouslyWithSuccessCallback() throws Exception {
		final UploadParameter param = UploadParameter.createForFileUpload(SMALL_FILE, PRIVATE_KEY_1)
				.build();
		final CompletableFuture<UploadResult> toPopulateOnSuccess = new CompletableFuture<>();

		unitUnderTest.uploadAsync(param, AsyncCallback.create(toPopulateOnSuccess::complete, null));
		final UploadResult result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
	}

	@Test
	public void shouldUploadAsynchronouslyWithFailureCallback() throws Exception {
		final UploadParameter param = UploadParameter.createForFileUpload(SMALL_FILE, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
				.build();
		final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

		unitUnderTest.uploadAsync(param, AsyncCallback.create(null, toPopulateOnFailure::complete));
		final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

		assertThat(throwable, instanceOf(UploadFailureException.class));
	}
}