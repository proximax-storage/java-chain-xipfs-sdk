package io.proximax.integration.storageconnection;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.StorageConnection;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.integration.TestDataRepository.logAndSaveResult;
import static io.proximax.testsupport.Constants.TEST_STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_storageConnectionIntegrationTest {

	private Uploader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Uploader(ConnectionConfig.createWithStorageConnection(
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
	public void shouldUploadToStorageConnection() {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getData().getDataHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadToStorageConnection");
	}

}
