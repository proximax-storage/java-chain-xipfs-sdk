package io.proximax.download;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.testsupport.TestHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PASSWORD;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_PARTS;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_TOTAL_PART_COUNT;
import static io.proximax.testsupport.Constants.SMALL_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class DownloadData_privacyStrategyIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadDataWithPlainPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.plainPrivacy()
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(result.getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataWithSecuredWithNemKeysPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.securedWithNemKeysPrivacyStrategy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(result.getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataWithSecuredWithPasswordPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.securedWithPasswordPrivacyStrategy(PASSWORD)
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(result.getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataWithSecuredWithShamirSecretSharingPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy", "dataList[0].dataHash");
		final DownloadDataParameter param =
				DownloadDataParameter.create(dataHash)
						.securedWithShamirSecretSharingPrivacyStrategy(
								SHAMIR_SECRET_TOTAL_PART_COUNT,
								SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
								SHAMIR_SECRET_PARTS)
						.build();

		final DownloadDataResult result = unitUnderTest.downloadData(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(result.getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

}
