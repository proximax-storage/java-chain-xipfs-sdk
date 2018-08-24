package io.proximax.integration.download;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.async.AsyncCallback;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.Download;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.model.PrivacyType;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Download_downloadAsyncIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadAsynchronouslyWithoutCallback() throws Exception {
		final String transactionHash = TestHelper.getData("Upload_uploadIntegrationTest.shouldUploadWithAllDetails", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.securedWithNemKeysPrivacyStrategy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();

		final AsyncTask asyncTask = unitUnderTest.downloadAsync(param, null);
		while (!asyncTask.isDone()) {
			Thread.sleep(50);
		}

		assertThat(asyncTask.isDone(), is(true));
	}

	@Test
	public void shouldDownloadAsynchronouslyWithSuccessCallback() throws Exception {
		final String transactionHash = TestHelper.getData("Upload_uploadIntegrationTest.shouldUploadWithAllDetails", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.securedWithNemKeysPrivacyStrategy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();
		final CompletableFuture<DownloadResult> toPopulateOnSuccess = new CompletableFuture<>();

		unitUnderTest.downloadAsync(param, AsyncCallback.create(toPopulateOnSuccess::complete, null));
		final DownloadResult result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDescription(), is("root description"));
		assertThat(result.getVersion(), is("1.0"));
		assertThat(result.getPrivacySearchTag(), is("nemkeys"));
		assertThat(result.getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(result.getDataList().get(0).getData(), is(notNullValue()));
		assertThat(result.getDataList().get(0).getContentType(), is("text/plain"));
		assertThat(result.getDataList().get(0).getDescription(), is("byte array description"));
		assertThat(result.getDataList().get(0).getName(), is("byte array"));
		assertThat(result.getDataList().get(0).getMetadata(), is(singletonMap("key1", "val1")));
	}

	@Test
	public void shouldDownloadAsynchronouslyWithFailureCallback() throws Exception {
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
						.plainPrivacy()
						.build();
		final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

		unitUnderTest.downloadAsync(param, AsyncCallback.create(null, toPopulateOnFailure::complete));
		final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

		assertThat(throwable, instanceOf(DownloadFailureException.class));
	}

}
