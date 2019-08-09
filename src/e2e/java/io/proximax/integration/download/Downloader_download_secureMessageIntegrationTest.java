package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.testsupport.Constants.TEST_STRING;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_download_secureMessageIntegrationTest {

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
	public void shouldDownloadWithSecureMessageAsSender() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withAccountPrivateKey(IntegrationTestConfig.getPrivateKey1())
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result.getData().getByteStream())), is(TEST_STRING));
		assertThat(result.getData().getContentType(), is("text/plain"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("string description"));
		assertThat(result.getData().getName(), is("string name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("keystring", "valstring")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test
	public void shouldDownloadWithSecureMessageAsRecipient() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withAccountPrivateKey(IntegrationTestConfig.getPrivateKey2())
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result.getData().getByteStream())), is(TEST_STRING));
		assertThat(result.getData().getContentType(), is("text/plain"));
		assertThat(result.getData().getDataHash(), is(notNullValue()));
		assertThat(result.getData().getDescription(), is("string description"));
		assertThat(result.getData().getName(), is("string name"));
		assertThat(result.getData().getMetadata(), is(singletonMap("keystring", "valstring")));
		assertThat(result.getData().getTimestamp(), is(notNullValue()));
	}

	@Test(expected = DownloadFailureException.class)
	public void failDownloadWithWrongPrivateKey() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withAccountPrivateKey(SAMPLE_ANOTHER_PRIVATE_KEY)
				.build();

		unitUnderTest.download(param);
	}

	@Test(expected = DownloadFailureException.class)
	public void failDownloadWithNoPrivateKey() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_secureMessageIntegrationTest.shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.build();

		unitUnderTest.download(param);
	}
}
