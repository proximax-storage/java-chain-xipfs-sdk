package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.upload.ByteArrayParameterData;
import io.proximax.upload.FileParameterData;
import io.proximax.upload.FilesAsZipParameterData;
import io.proximax.upload.PathParameterData;
import io.proximax.upload.StringParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import io.proximax.upload.UrlResourceParameterData;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.integration.TestDataRepository.logAndSaveResult;
import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.model.Constants.SCHEMA_VERSION;
import static io.proximax.testsupport.Constants.TEST_HTML_FILE;
import static io.proximax.testsupport.Constants.TEST_IMAGE_PNG_FILE;
import static io.proximax.testsupport.Constants.TEST_PATH_FILE;
import static io.proximax.testsupport.Constants.TEST_PDF_FILE_1;
import static io.proximax.testsupport.Constants.TEST_STRING;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class Uploader_integrationTest {

	private Uploader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Uploader(ConnectionConfig.create(
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
	public void shouldReturnVersion() throws Exception {
		final UploadParameter param = UploadParameter
				.createForByteArrayUpload(FileUtils.readFileToByteArray(TEST_PDF_FILE_1), IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getVersion(), is(SCHEMA_VERSION));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldReturnVersion");
	}

	@Test
	public void shouldUploadByteArray() throws Exception {
		final UploadParameter param = UploadParameter
				.createForByteArrayUpload(FileUtils.readFileToByteArray(TEST_PDF_FILE_1), IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(nullValue()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadByteArray");
	}

	@Test
	public void shouldUploadByteArrayWithCompleteDetails() throws Exception {
		final UploadParameter param = UploadParameter
				.createForByteArrayUpload(
						ByteArrayParameterData.create(FileUtils.readFileToByteArray(TEST_PDF_FILE_1), "byte array description", "byte array",
								"application/pdf", singletonMap("bytearraykey", "bytearrayval")),
						IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("application/pdf"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("byte array description"));
		assertThat(result.getData().getName(), is("byte array"));
		assertThat(result.getData().getMetadata(), is(singletonMap("bytearraykey", "bytearrayval")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadByteArrayWithCompleteDetails");
	}

	@Test
	public void shouldUploadFile() throws Exception {
		final UploadParameter param = UploadParameter
				.createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is("test_text_file.txt"));
		assertThat(result.getData().getMetadata(), is(nullValue()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFile");
	}

	@Test
	public void shouldUploadFileWithCompleteDetails() throws Exception {
		final UploadParameter param = UploadParameter
				.createForFileUpload(
						FileParameterData.create(TEST_TEXT_FILE, "file description", "file name",
								"text/plain", singletonMap("filekey", "filename")),
						IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("text/plain"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("file description"));
		assertThat(result.getData().getName(), is("file name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("filekey", "filename")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithCompleteDetails");
	}

	@Test
	public void shouldUploadUrlResource() throws Exception {
		final UploadParameter param = UploadParameter
				.createForUrlResourceUpload(TEST_IMAGE_PNG_FILE.toURI().toURL(), IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(nullValue()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadUrlResource");
	}

	@Test
	public void shouldUploadUrlResourceWithCompleteDetails() throws Exception {
		final UploadParameter param = UploadParameter
				.createForUrlResourceUpload(
						UrlResourceParameterData.create(TEST_IMAGE_PNG_FILE.toURI().toURL(),"url description",
								"url name", "image/png", singletonMap("urlkey", "urlval")),
						IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("image/png"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("url description"));
		assertThat(result.getData().getName(), is("url name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("urlkey", "urlval")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadUrlResourceWithCompleteDetails");
	}

	@Test
	public void shouldUploadFilesAsZip() throws Exception {
		final UploadParameter param = UploadParameter
				.createForFilesAsZipUpload(asList(TEST_TEXT_FILE, TEST_HTML_FILE), IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("application/zip"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(nullValue()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFilesAsZip");
	}

	@Test
	public void shouldUploadFilesAsZipWithCompleteDetails() throws Exception {
		final UploadParameter param = UploadParameter
				.createForFilesAsZipUpload(
						FilesAsZipParameterData.create(asList(TEST_TEXT_FILE, TEST_HTML_FILE), "zip description",
								"zip name", singletonMap("zipkey", "zipvalue")),
						IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("application/zip"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("zip description"));
		assertThat(result.getData().getName(), is("zip name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("zipkey", "zipvalue")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFilesAsZipWithCompleteDetails");
	}

	@Test
	public void shouldUploadString() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(nullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(nullValue()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadString");
	}

	@Test
	public void shouldUploadStringWithCompleteDetails() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(
						StringParameterData.create(TEST_STRING, "UTF-8", "string description", "string name",
								"text/plain", singletonMap("keystring", "valstring")),
						IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is("text/plain"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("string description"));
		assertThat(result.getData().getName(), is("string name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("keystring", "valstring")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadStringWithCompleteDetails");
	}

	@Test
	public void shouldUploadPath() throws Exception {
		final UploadParameter param = UploadParameter
				.createForPathUpload(TEST_PATH_FILE, IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is(nullValue()));
		assertThat(result.getData().getName(), is(nullValue()));
		assertThat(result.getData().getMetadata(), is(nullValue()));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadPath");
	}

	@Test
	public void shouldUploadPathWithCompleteDetails() throws Exception {
		final UploadParameter param = UploadParameter
				.createForPathUpload(
						PathParameterData.create(TEST_PATH_FILE, "path description", "path name", singletonMap("pathkey", "pathval")),
						IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		assertThat(result.getData().getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("path description"));
		assertThat(result.getData().getName(), is("path name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("pathkey", "pathval")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadPathWithCompleteDetails");
	}
}
