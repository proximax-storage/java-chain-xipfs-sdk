package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.exceptions.DownloadForDataTypeNotSupportedException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.model.Constants.SCHEMA_VERSION;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class Downloader_download_integrationTest {

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

	@Test(expected = DownloadFailureException.class)
	public void failWhenInvalidTransactionHash() {
		final DownloadParameter param = DownloadParameter
				.create("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		unitUnderTest.download(param);
	}

	@Test
	public void shouldDownloadWithVersion() {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldReturnVersion", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getVersion(), is(SCHEMA_VERSION));
	}

	@Test
	public void shouldDownloadByteArray() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadByteArray", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(emptyMap()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadByteArrayWithCompleteDetails() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadByteArrayWithCompleteDetails", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("application/pdf"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("byte array description"));
		assertThat(result.getData().getName(), is("byte array"));
		assertThat(result.getData().getMetadata(), is(singletonMap("bytearraykey", "bytearrayval")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadFile() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadFile", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is("test_text_file.txt"));
		assertThat(result.getData().getMetadata(), is(emptyMap()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadFileWithCompleteDetails() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadFileWithCompleteDetails", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("text/plain"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("file description"));
		assertThat(result.getData().getName(), is("file name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("filekey", "filename")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadUrlResource() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadUrlResource", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(emptyMap()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadUrlResourceWithCompleteDetails() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadUrlResourceWithCompleteDetails", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("image/png"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("url description"));
		assertThat(result.getData().getName(), is("url name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("urlkey", "urlval")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadFilesAsZip() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadFilesAsZip", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("application/zip"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(emptyMap()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadFilesAsZipWithCompleteDetails() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadFilesAsZipWithCompleteDetails", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("application/zip"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("zip description"));
		assertThat(result.getData().getName(), is("zip name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("zipkey", "zipvalue")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadString() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadString", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(emptyMap()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadStringpWithCompleteDetails() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadStringWithCompleteDetails", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.toByteArray(result.getData().getByteStream()), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("text/plain"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("string description"));
		assertThat(result.getData().getName(), is("string name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("keystring", "valstring")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test(expected = DownloadForDataTypeNotSupportedException.class)
	public void failDownloadOnGetByteStreamWhenContentTypeIsDirectory() {
		final String transactionHash = TestDataRepository
				.getData("Uploader_integrationTest.shouldUploadPath", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult download = unitUnderTest.download(param);
		download.getData().getByteStream();
	}
}
