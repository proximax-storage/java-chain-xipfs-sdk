package io.proximax.integration.upload;

import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.UploadFailureException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.privacy.strategy.CustomPrivacyStrategy;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.proximax.testsupport.Constants.TEST_PDF_FILE_1;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_asyncIntegrationTest {

	private Uploader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Uploader(ConnectionConfig.createWithLocalIpfsConnection(
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
	public void shouldUploadAsynchronouslyWithoutCallback() throws Exception {
		final UploadParameter param = UploadParameter
				.createForByteArrayUpload(FileUtils.readFileToByteArray(TEST_PDF_FILE_1), IntegrationTestConfig.getPrivateKey1())
				.build();

		final AsyncTask asyncTask = unitUnderTest.uploadAsync(param, null);
		while (!asyncTask.isDone()) {
			Thread.sleep(50);
		}

		assertThat(asyncTask.isDone(), is(true));
	}

	@Test
	public void shouldUploadAsynchronouslyWithSuccessCallback() throws Exception {
		final UploadParameter param = UploadParameter
				.createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
				.build();
		final CompletableFuture<UploadResult> toPopulateOnSuccess = new CompletableFuture<>();

		unitUnderTest.uploadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess::complete, null));
		final UploadResult result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
	}

	@Test
	public void shouldUploadAsynchronouslyWithFailureCallback() throws Exception {
		final UploadParameter param = UploadParameter
				.createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
				.withPrivacyStrategy(new NotImplementedPrivacyStrategy())
				.build();
		final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

		unitUnderTest.uploadAsync(param, AsyncCallbacks.create(null, toPopulateOnFailure::complete));
		final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

		assertThat(throwable, instanceOf(UploadFailureException.class));
	}

	@Test
	public void shouldUploadParallelRequestsAsynchronously() throws Exception {
		final UploadParameter param = UploadParameter
				.createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
				.build();
		final CompletableFuture<UploadResult> toPopulateOnSuccess1 = new CompletableFuture<>();
		final CompletableFuture<UploadResult> toPopulateOnSuccess2 = new CompletableFuture<>();
		final CompletableFuture<UploadResult> toPopulateOnSuccess3 = new CompletableFuture<>();

		unitUnderTest.uploadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess1::complete, null));
		unitUnderTest.uploadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess2::complete, null));
		unitUnderTest.uploadAsync(param, AsyncCallbacks.create(toPopulateOnSuccess3::complete, null));

		final UploadResult result1 = toPopulateOnSuccess1.get(5, TimeUnit.SECONDS);
		final UploadResult result2 = toPopulateOnSuccess2.get(5, TimeUnit.SECONDS);
		final UploadResult result3 = toPopulateOnSuccess3.get(5, TimeUnit.SECONDS);

		assertThat(result1, is(notNullValue()));
		assertThat(result1.getTransactionHash(), is(notNullValue()));
		assertThat(result2, is(notNullValue()));
		assertThat(result2.getTransactionHash(), is(notNullValue()));
		assertThat(result3, is(notNullValue()));
		assertThat(result3.getTransactionHash(), is(notNullValue()));
	}

	private class NotImplementedPrivacyStrategy extends CustomPrivacyStrategy{
		@Override
		public InputStream encryptStream (InputStream byteStream){
			throw new RuntimeException("not implemented");
		}

		@Override
		public InputStream decryptStream (InputStream byteStream){
			return byteStream;
		}
	}
}
