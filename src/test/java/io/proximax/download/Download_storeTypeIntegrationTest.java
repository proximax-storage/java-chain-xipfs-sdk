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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Download_storeTypeIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadAsBlockStoreType() {
		final String transactionHash = TestHelper.getData("Upload_storageTypeIntegrationTest.shouldUploadAsBlockStoreType", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getStoreType(), is(StoreType.BLOCK));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}

	@Test
	public void shouldDownloadAsResourceStoreType() {
		final String transactionHash = TestHelper.getData("Upload_storageTypeIntegrationTest.shouldUploadAsResourceStoreType", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getStoreType(), is(StoreType.RESOURCE));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}
}
