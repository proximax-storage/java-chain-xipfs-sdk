package io.proximax.download;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class DownloadData_downloadIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test(expected = DownloadFailureException.class)
	public void failWhenInvalidDataHash() {
		final DownloadDataParameter param =
				DownloadDataParameter.create("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		unitUnderTest.downloadData(param);
	}

	@Test
	public void shouldDownloadDataByDataHash() {
		final String dataHash = TestHelper.getData("Upload_uploadIntegrationTest.shouldUploadMultipleData", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash).build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getData(), is(notNullValue()));
	}

	@Test(expected = DownloadFailureException.class)
	public void failWhenInvalidRootDataHash() {
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", null).build();

		unitUnderTest.download(param);
	}

}
