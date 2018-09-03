package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.Downloader;
import io.proximax.download.DownloadDataParameter;
import io.proximax.download.DownloadDataResult;
import io.proximax.model.BlockchainNetwork;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_downloadData_digestIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetwork.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadDataUsingDataHashOnly() {
		final String dataHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(result.getData()), is(STRING_TEST));
	}

	@Test
	public void shouldDownloadDataUsingDataHashAndDigest() {
		final String dataHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "dataList[0].dataHash");
		final String digest = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "dataList[0].digest");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.digest(digest)
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(result.getData()), is(STRING_TEST));
	}
}
