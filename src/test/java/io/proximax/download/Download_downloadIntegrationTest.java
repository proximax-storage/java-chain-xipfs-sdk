package io.proximax.download;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.model.PrivacyType;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Download_downloadIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test(expected = DownloadFailureException.class)
	public void failWhenInvalidTransactionHash() {
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		unitUnderTest.download(param);
	}

	@Test
	public void shouldDownloadByTransactionHash() {
		final String transactionHash = TestHelper.getData("Upload_uploadIntegrationTest.shouldUploadAllDataTypes", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(6));
		result.getDataList().stream().forEach(data -> {
			if (!data.getContentType().equals(PATH_UPLOAD_CONTENT_TYPE))
				assertThat(data.getData(), is(notNullValue()));
		});
	}

	@Test(expected = DownloadFailureException.class)
	public void failWhenInvalidRootDataHash() {
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null).build();

		unitUnderTest.download(param);
	}

	@Test
	public void shouldDownloadByRootDataHash() {
		final String rootDataHash = TestHelper.getData("Upload_uploadIntegrationTest.shouldUploadAllDataTypes", "rootDataHash");
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash(rootDataHash, null).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(6));
		result.getDataList().stream().forEach(data -> {
			if (!data.getContentType().equals(PATH_UPLOAD_CONTENT_TYPE))
				assertThat(data.getData(), is(notNullValue()));
		});
	}

	@Test
	public void shouldDownloadCompleteDetails() {
		final String transactionHash = TestHelper.getData("Upload_uploadIntegrationTest.shouldUploadWithAllDetails", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.securedWithNemKeysPrivacyStrategy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();

		final DownloadResult result = unitUnderTest.download(param);

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

}
