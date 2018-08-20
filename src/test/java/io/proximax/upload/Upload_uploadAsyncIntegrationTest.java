package io.proximax.upload;

import com.google.common.io.Files;
import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.async.AsyncCallback;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.HTML_FILE;
import static io.proximax.testsupport.Constants.IMAGE_FILE;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PDF_FILE1;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static io.proximax.testsupport.Constants.SMALL_FILE;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Upload_uploadAsyncIntegrationTest {

	private Upload unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Upload(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldUploadAsynchrounslyWithoutCallback() throws Exception {
		final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
				.addByteArray(FileUtils.readFileToByteArray(PDF_FILE1))
				.addFile(SMALL_FILE)
				.addUrlResource(IMAGE_FILE.toURI().toURL())
				.addFilesAsZip(asList(SMALL_FILE, HTML_FILE))
				.addString(STRING_TEST)
				.build();

		final AsyncTask asyncTask = unitUnderTest.uploadAsync(param, null);
		while (!asyncTask.isDone()) {
			Thread.sleep(50);
		}

		assertThat(asyncTask.isDone(), is(true));
	}

	@Test
	public void shouldUploadAsynchrounslyWithSuccessCallback() throws Exception {
		final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
				.addByteArray(FileUtils.readFileToByteArray(PDF_FILE1))
				.addFile(SMALL_FILE)
				.addUrlResource(IMAGE_FILE.toURI().toURL())
				.addFilesAsZip(asList(SMALL_FILE, HTML_FILE))
				.addString(STRING_TEST)
				.build();
		final CompletableFuture<UploadResult> toPopulateOnSuccess = new CompletableFuture<>();

		unitUnderTest.uploadAsync(param, AsyncCallback.create(toPopulateOnSuccess::complete, null));
		final UploadResult result = toPopulateOnSuccess.get();

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getDigest(), is(notNullValue()));
		assertThat(result.getRootDataHash(), is(notNullValue()));
		assertThat(result.getRootData(), is(notNullValue()));
		assertThat(result.getRootData().getDataList(), hasSize(5));
		result.getRootData().getDataList().forEach(data -> {
			assertThat(data.getContentType(), is(notNullValue()));
			assertThat(data.getDataHash(), is(notNullValue()));
			assertThat(data.getDigest(), is(notNullValue()));
			assertThat(data.getTimestamp(), is(notNullValue()));
		});
	}

}
