package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.BlockchainNetwork;
import io.proximax.upload.StringParameterData;
import io.proximax.upload.Upload;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
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
import static org.hamcrest.core.IsNull.nullValue;

public class Upload_computeDigestIntegrationTest {

	private Upload unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Upload(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetwork.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldUploadWithEnabledComputeDigest() throws Exception {
		final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
				.addString(StringParameterData.create(STRING_TEST).build())
				.computeDigest(true)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getDigest(), is(notNullValue()));
		assertThat(result.getRootDataHash(), is(notNullValue()));
		assertThat(result.getRootData().getDataList(), hasSize(1));
		assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
		assertThat(result.getRootData().getDataList().get(0).getDigest(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithEnabledComputeDigest");
	}

	@Test
	public void shouldUploadWithDisabledComputeDigest() throws Exception {
		final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
				.addString(StringParameterData.create(STRING_TEST).build())
				.computeDigest(false)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getDigest(), is(nullValue()));
		assertThat(result.getRootDataHash(), is(notNullValue()));
		assertThat(result.getRootData().getDataList(), hasSize(1));
		assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
		assertThat(result.getRootData().getDataList().get(0).getDigest(), is(nullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithDisabledComputeDigest");
	}
}
