package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.TEST_PASSWORD;
import static io.proximax.testsupport.Constants.TEST_PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.TEST_PUBLIC_KEY_2;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_SHARES;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_THRESHOLD;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_TOTAL_SHARES;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_privacyIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(
						IntegrationTestConfig.getBlockchainNetworkType(),
						IntegrationTestConfig.getBlockchainApiHost(),
						IntegrationTestConfig.getBlockchainApiPort(),
						IntegrationTestConfig.getBlockchainApiProtocol()),
				new IpfsConnection(
						IntegrationTestConfig.getIpfsApiHost(),
						IntegrationTestConfig.getIpfsApiPort())));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithPlainPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.withPlainPrivacy()
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithSecuredWithNemKeysPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.withNemKeysPrivacy(TEST_PRIVATE_KEY_1, TEST_PUBLIC_KEY_2)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithSecuredWithPasswordPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.withPasswordPrivacy(TEST_PASSWORD)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingTransactionHashWithSecuredWithShamirSecretSharingPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
				"transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash)
						.withShamirSecretSharingPrivacy(
								TEST_SHAMIR_SECRET_TOTAL_SHARES,
								TEST_SHAMIR_SECRET_THRESHOLD,
								TEST_SHAMIR_SECRET_SHARES)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithPlainPrivacyStrategy() throws IOException {
		final String dataHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.withPlainPrivacy()
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithSecuredWithNemKeysPrivacyStrategy() throws IOException {
		final String dataHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.withNemKeysPrivacy(TEST_PRIVATE_KEY_1, TEST_PUBLIC_KEY_2)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithSecuredWithPasswordPrivacyStrategy() throws IOException {
		final String dataHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.withPasswordPrivacy(TEST_PASSWORD)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadDataUsingDataHashWithSecuredWithShamirSecretSharingPrivacyStrategy() throws IOException {
		final String dataHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
				"dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash)
						.withShamirSecretSharingPrivacy(
								TEST_SHAMIR_SECRET_TOTAL_SHARES,
								TEST_SHAMIR_SECRET_THRESHOLD,
								TEST_SHAMIR_SECRET_SHARES)
						.build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

}
