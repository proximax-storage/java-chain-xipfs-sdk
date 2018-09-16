package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.model.PrivacyType;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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

public class Downloader_download_privacyStrategyIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestConfig.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestConfig.getIpfsMultiAddress())));
	}

	@Test
	public void shouldDownloadWithPlainPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
				"transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withPlainPrivacy()
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadWithSecuredWithNemKeysPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
				"transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withNemKeysPrivacy(TEST_PRIVATE_KEY_1, TEST_PUBLIC_KEY_2)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadWithSecuredWithPasswordPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
				"transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withPasswordPrivacy(TEST_PASSWORD)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.PASSWORD.getValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadWithSecuredWithShamirSecretSharingPrivacyStrategy() throws IOException {
		final String transactionHash = TestDataRepository.getData(
				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
				"transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withShamirSecretSharingPrivacy(
						TEST_SHAMIR_SECRET_TOTAL_SHARES,
						TEST_SHAMIR_SECRET_THRESHOLD,
						TEST_SHAMIR_SECRET_SHARES)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getPrivacyType(), is(PrivacyType.SHAMIR.getValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}
}
