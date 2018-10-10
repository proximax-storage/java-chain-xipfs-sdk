package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DirectDownloadFailureException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.TEST_IMAGE_PNG_FILE;
import static io.proximax.testsupport.Constants.TEST_PDF_FILE_1;
import static io.proximax.testsupport.Constants.TEST_STRING;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_integrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.createWithLocalIpfsConnection(
				new BlockchainNetworkConnection(
						IntegrationTestConfig.getBlockchainNetworkType(),
						IntegrationTestConfig.getBlockchainApiHost(),
						IntegrationTestConfig.getBlockchainApiPort(),
						IntegrationTestConfig.getBlockchainApiProtocol()),
				new IpfsConnection(
						IntegrationTestConfig.getIpfsApiHost(),
						IntegrationTestConfig.getIpfsApiPort())));
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failWhenInvalidTransactionHash() {
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		unitUnderTest.directDownload(param);
	}

	@Test
	public void shouldDownloadByteArrayByTransactionHash() throws IOException {
		final String transactionHash =
				TestDataRepository.getData("Uploader_integrationTest.shouldUploadByteArray", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_PDF_FILE_1))))));
	}

	@Test
	public void shouldDownloadFileByTransactionHash() throws IOException {
		final String transactionHash =
				TestDataRepository.getData("Uploader_integrationTest.shouldUploadFile", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadUrlResourceByTransactionHash() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadUrlResource", "transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_IMAGE_PNG_FILE))))));
	}

	@Test
	public void shouldDownloadFilesAsZipByTransactionHash() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadFilesAsZip", "transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(IOUtils.toByteArray(result), is(notNullValue()));
	}

	@Test
	public void shouldDownloadStringByTransactionHash() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadString", "transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failDownloadByTransactionHashWhenContentTypeIsDirectory() {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadPath", "transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		unitUnderTest.directDownload(param);
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failDownloadWhenInvalidTransactionHash() throws IOException {
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		unitUnderTest.directDownload(param);
	}

	@Test
	public void shouldDownloadByteArrayByDataHash() throws IOException {
		final String dataHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadByteArray", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_PDF_FILE_1))))));
	}

	@Test
	public void shouldDownloadFileByDataHash() throws IOException {
		final String dataHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadFile", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
	}

	@Test
	public void shouldDownloadUrlResourceByDataHash() throws IOException {
		final String dataHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadUrlResource", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_IMAGE_PNG_FILE))))));
	}

	@Test
	public void shouldDownloadFilesAsZipByDataHash() throws IOException {
		final String dataHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadFilesAsZip", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(IOUtils.toByteArray(result), is(notNullValue()));
	}

	@Test
	public void shouldDownloadStringByDataHash() throws IOException {
		final String dataHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadString", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failDownloadByDataHashWhenContentTypeIsDirectory() {
		final String dataHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadPath", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		unitUnderTest.directDownload(param);
	}
}
