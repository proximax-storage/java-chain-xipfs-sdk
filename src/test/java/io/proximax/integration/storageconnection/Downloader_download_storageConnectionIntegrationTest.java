package io.proximax.integration.storageconnection;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.connection.StorageConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.testsupport.Constants.TEST_STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_download_storageConnectionIntegrationTest {

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
	public void shouldDownloadFromStorageConnection() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_storageConnectionIntegrationTest.shouldUploadToStorageConnection", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result.getData().getByteStream())), is(TEST_STRING));
	}
}
