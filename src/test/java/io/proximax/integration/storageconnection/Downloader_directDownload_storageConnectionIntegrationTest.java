package io.proximax.integration.storageconnection;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.connection.StorageConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.TEST_STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_storageConnectionIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.createWithStorageConnection(
				new BlockchainNetworkConnection(
						IntegrationTestConfig.getBlockchainNetworkType(),
						IntegrationTestConfig.getBlockchainApiHost(),
						IntegrationTestConfig.getBlockchainApiPort(),
						IntegrationTestConfig.getBlockchainApiProtocol()),
				new StorageConnection(
						IntegrationTestConfig.getStorageNodeApiHost(),
						IntegrationTestConfig.getStorageNodeApiPort(),
						IntegrationTestConfig.getStorageNodeApiProtocol(),
						IntegrationTestConfig.getStorageNodeApiBearerToken(),
						IntegrationTestConfig.getStorageNodeApiNemAddress())));
	}

	@Test
	public void shouldDirectDownloadFromStorageConnectionUsingTransactionHash() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_storageConnectionIntegrationTest.shouldUploadToStorageConnection", "transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
	}

	@Test
	public void shouldDirectDownloadFromStorageConnectionUsingDataHash() throws IOException {
		final String dataHash = TestDataRepository
				.getData("Uploader_storageConnectionIntegrationTest.shouldUploadToStorageConnection", "dataHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));
	}
}
