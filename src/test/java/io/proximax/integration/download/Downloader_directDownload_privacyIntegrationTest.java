package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.TestHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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

public class Downloader_directDownload_privacyIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithPlainPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.plainPrivacy()
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithSecuredWithNemKeysPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.securedWithNemKeysPrivacy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithSecuredWithPasswordPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.securedWithPasswordPrivacy(PASSWORD)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithSecuredWithShamirSecretSharingPrivacyStrategy() throws IOException {
		final String transactionHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.securedWithShamirSecretSharingPrivacy(
								SHAMIR_SECRET_TOTAL_PART_COUNT,
								SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
								SHAMIR_SECRET_PARTS)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithPlainPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.plainPrivacy()
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithSecuredWithNemKeysPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.securedWithNemKeysPrivacy(PRIVATE_KEY_1, PUBLIC_KEY_2)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithSecuredWithPasswordPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.securedWithPasswordPrivacy(PASSWORD)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithSecuredWithShamirSecretSharingPrivacyStrategy() throws IOException {
		final String dataHash = TestHelper.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.securedWithShamirSecretSharingPrivacy(
								SHAMIR_SECRET_TOTAL_PART_COUNT,
								SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
								SHAMIR_SECRET_PARTS)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

}
