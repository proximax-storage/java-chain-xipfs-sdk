package io.proximax.integration.download;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.Download;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.model.PrivacyType;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Download_privacyStrategyIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(NetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadWithPlainPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.plainPrivacy()
						.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
		assertThat(result.getPrivacySearchTag(), is("plain"));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(ArrayUtils.toObject(result.getDataList().get(0).getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadWithSecuredWithNemKeysPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.securedWithNemKeysPrivacyStrategy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
		assertThat(result.getPrivacySearchTag(), is("nem"));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(ArrayUtils.toObject(result.getDataList().get(0).getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadWithSecuredWithPasswordPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.securedWithPasswordPrivacyStrategy(PASSWORD)
						.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.PASSWORD.getValue()));
		assertThat(result.getPrivacySearchTag(), is("pass"));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(ArrayUtils.toObject(result.getDataList().get(0).getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadWithSecuredWithShamirSecretSharingPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData("Upload_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash)
						.securedWithShamirSecretSharingPrivacyStrategy(
								SHAMIR_SECRET_TOTAL_PART_COUNT,
								SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
								SHAMIR_SECRET_PARTS)
						.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.SHAMIR.getValue()));
		assertThat(result.getPrivacySearchTag(), is("shamir"));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(ArrayUtils.toObject(result.getDataList().get(0).getData()),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}
}
