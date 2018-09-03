package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.model.BlockchainNetwork;
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

public class Downloader_download_integrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetwork.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
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
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadAllDataTypes", "transactionHash");
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
		final String rootDataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadAllDataTypes", "rootDataHash");
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
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadWithAllDetails", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.securedWithNemKeysPrivacy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDescription(), is("root description"));
		assertThat(result.getVersion(), is("1.0"));
		assertThat(result.getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(result.getDataList().get(0).getData(), is(notNullValue()));
		assertThat(result.getDataList().get(0).getContentType(), is("text/plain"));
		assertThat(result.getDataList().get(0).getDescription(), is("byte array description"));
		assertThat(result.getDataList().get(0).getName(), is("byte array"));
		assertThat(result.getDataList().get(0).getMetadata(), is(singletonMap("key1", "val1")));
	}

}
