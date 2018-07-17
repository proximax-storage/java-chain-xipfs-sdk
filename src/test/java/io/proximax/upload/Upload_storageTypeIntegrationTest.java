package io.proximax.upload;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.StoreType;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static io.proximax.testsupport.TestHelper.logAndSaveResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Upload_storageTypeIntegrationTest {

	private Upload unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Upload(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldUploadAsBlockStoreType() throws Exception {
		final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
				.addString(StringParameterData.create(STRING_TEST).build())
				.storeType(StoreType.BLOCK)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getDigest(), is(notNullValue()));
		assertThat(result.getRootDataHash(), is(notNullValue()));
		assertThat(result.getRootData(), is(notNullValue()));
		assertThat(result.getRootData().getStoreType(), is(StoreType.BLOCK));
		assertThat(result.getRootData().getDataList(), hasSize(1));
		assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
		assertThat(result.getRootData().getDataList().get(0).getDigest(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadAsBlockStoreType");
	}

	@Test
	public void shouldUploadAsResourceStoreType() throws Exception {
		final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
				.addString(StringParameterData.create(STRING_TEST).build())
				.storeType(StoreType.RESOURCE)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getDigest(), is(notNullValue()));
		assertThat(result.getRootDataHash(), is(notNullValue()));
		assertThat(result.getRootData(), is(notNullValue()));
		assertThat(result.getRootData().getStoreType(), is(StoreType.RESOURCE));
		assertThat(result.getRootData().getDataList(), hasSize(1));
		assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
		assertThat(result.getRootData().getDataList().get(0).getDigest(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadAsResourceStoreType");
	}
}
