package io.proximax.download;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.StoreType;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class DownloadData_storeTypeIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadDataAsBlockStoreType() {
		final String dataHash = TestHelper.getData("Upload_storageTypeIntegrationTest.shouldUploadAsBlockStoreType", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.storeType(StoreType.BLOCK)
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(result.getData()), is(STRING_TEST));
	}

	@Test
	public void shouldDownloadDataAsResourceStoreType() {
		final String dataHash = TestHelper.getData("Upload_storageTypeIntegrationTest.shouldUploadAsResourceStoreType", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.storeType(StoreType.RESOURCE)
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(result.getData()), is(STRING_TEST));
	}
}
