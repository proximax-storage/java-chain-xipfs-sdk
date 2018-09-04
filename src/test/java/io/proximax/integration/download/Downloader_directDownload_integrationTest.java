package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DirectDownloadFailureException;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.TestHelper;
import io.proximax.upload.FilesAsZipParameterData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.HTML_FILE;
import static io.proximax.testsupport.Constants.IMAGE_FILE;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PDF_FILE1;
import static io.proximax.testsupport.Constants.SMALL_FILE;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_integrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failWhenInvalidTransactionHash() {
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		unitUnderTest.directDownload(param);
	}

	@Test
	public void shouldDownloadByteArrayByTransactionHash() throws IOException {
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadByteArray", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(ArrayUtils.toObject(bytes),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(PDF_FILE1))))));
	}

	@Test
	public void shouldDownloadFileByTransactionHash() throws IOException {
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadFile", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(ArrayUtils.toObject(bytes),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadUrlResourceByTransactionHash() throws IOException {
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadUrlResource", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(ArrayUtils.toObject(bytes),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(IMAGE_FILE))))));
	}

	@Test
	public void shouldDownloadFilesAsZipByTransactionHash() throws IOException {
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadFilesAsZip", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
	}

	@Test
	public void shouldDownloadStringByTransactionHash() {
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadString", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(new String(bytes), is(STRING_TEST));
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failDownloadByTransactionHashWhenContentTypeIsDirectory() {
		final String transactionHash = TestHelper.getData("Uploader_integrationTest.shouldUploadPath", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		unitUnderTest.directDownload(param);
	}

	@Test
	public void shouldDownloadByteArrayByDataHash() throws IOException {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadByteArray", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(ArrayUtils.toObject(bytes),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(PDF_FILE1))))));
	}

	@Test
	public void shouldDownloadFileByDataHash() throws IOException {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadFile", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(ArrayUtils.toObject(bytes),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(SMALL_FILE))))));
	}

	@Test
	public void shouldDownloadUrlResourceByDataHash() throws IOException {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadUrlResource", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(ArrayUtils.toObject(bytes),
				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(IMAGE_FILE))))));
	}

	@Test
	public void shouldDownloadFilesAsZipByDataHash() throws IOException {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadFilesAsZip", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
	}

	@Test
	public void shouldDownloadStringByDataHash() {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadString", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final byte[] bytes = unitUnderTest.directDownload(param);

		assertThat(bytes, is(notNullValue()));
		assertThat(new String(bytes), is(STRING_TEST));
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failDownloadByDataHashWhenContentTypeIsDirectory() {
		final String dataHash = TestHelper.getData("Uploader_integrationTest.shouldUploadPath", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		unitUnderTest.directDownload(param);
	}
}
