package io.proximax.integration.upload;

import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.UploadFailureException;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.privacy.strategy.CustomPrivacyStrategy;
import io.proximax.testsupport.IntegrationTestProperties;
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
		unitUnderTest = new Uploader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestProperties.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestProperties.getIpfsMultiAddress())));
	}

	@Test
	public void shouldUploadAsynchronouslyWithoutCallback() throws Exception {
		final UploadParameter param = UploadParameter
				.createForByteArrayUpload(FileUtils.readFileToByteArray(TEST_PDF_FILE_1), IntegrationTestProperties.getPrivateKey1())
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
				.createForFileUpload(TEST_TEXT_FILE, IntegrationTestProperties.getPrivateKey1())
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
				.createForFileUpload(TEST_TEXT_FILE, IntegrationTestProperties.getPrivateKey1())
				.withPrivacyStrategy(new NotImplementedPrivacyStrategy())
				.build();
		final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

		unitUnderTest.uploadAsync(param, AsyncCallbacks.create(null, toPopulateOnFailure::complete));
		final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

		assertThat(throwable, instanceOf(UploadFailureException.class));
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
