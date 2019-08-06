package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DirectDownloadFailureException;
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

public class Downloader_directDownload_secureMessageIntegrationTest {

	private static final String SAMPLE_ANOTHER_PRIVATE_KEY = "7FE209FAA5DE3E1EDEBD169091145788FF7B0847AD2FE04FB7706A660BFCAF0A";

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

	@Test
	public void shouldDirectDownloadWithSecureMessageAsSender() throws IOException {
		final String transactionHash =
				TestDataRepository.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter
				.createFromTransactionHash(transactionHash, IntegrationTestConfig.getPrivateKey1()).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));

	}

	@Test
	public void shouldDirectDownloadWithSecureMessageAsRecipient() throws IOException {
		final String transactionHash =
				TestDataRepository.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter
				.createFromTransactionHash(transactionHash, IntegrationTestConfig.getPrivateKey2()).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(TEST_STRING));

	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failDirectDownloadWithWrongPrivateKey() throws IOException {
		final String transactionHash =
				TestDataRepository.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter
				.createFromTransactionHash(transactionHash, SAMPLE_ANOTHER_PRIVATE_KEY).build();

		unitUnderTest.directDownload(param);
	}

	@Test(expected = DirectDownloadFailureException.class)
	public void failDirectDownloadWithNoPrivateKey() throws IOException {
		final String transactionHash =
				TestDataRepository.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter
				.createFromTransactionHash(transactionHash).build();

		unitUnderTest.directDownload(param);
	}

}
