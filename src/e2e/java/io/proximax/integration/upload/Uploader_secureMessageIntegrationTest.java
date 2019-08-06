package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.upload.StringParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.integration.TestDataRepository.logAndSaveResult;
import static io.proximax.testsupport.Constants.TEST_STRING;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_secureMessageIntegrationTest {

	private Uploader unitUnderTest;
	private ConnectionConfig connectionConfig;

	@Before
	public void setUp() {
		connectionConfig = ConnectionConfig.createWithLocalIpfsConnection(
				new BlockchainNetworkConnection(
						IntegrationTestConfig.getBlockchainNetworkType(),
						IntegrationTestConfig.getBlockchainApiHost(),
						IntegrationTestConfig.getBlockchainApiPort(),
						IntegrationTestConfig.getBlockchainApiProtocol()),
				new IpfsConnection(
						IntegrationTestConfig.getIpfsApiHost(),
						IntegrationTestConfig.getIpfsApiPort()));
		unitUnderTest = new Uploader(connectionConfig);
	}

	@Test
	public void shouldUploadWithUseBlockchainSecureMessage() {
		final UploadParameter param = UploadParameter
				.createForStringUpload(
						StringParameterData.create(TEST_STRING, "UTF-8", "string description", "string name",
								"text/plain", singletonMap("keystring", "valstring")),
						IntegrationTestConfig.getPrivateKey1())
				.withUseBlockchainSecureMessage(true)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithUseBlockchainSecureMessage");
	}

	@Test
	public void shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey() {
		final UploadParameter param = UploadParameter
				.createForStringUpload(
						StringParameterData.create(TEST_STRING, "UTF-8", "string description", "string name",
								"text/plain", singletonMap("keystring", "valstring")),
						IntegrationTestConfig.getPrivateKey1())
				.withRecipientPublicKey(IntegrationTestConfig.getPublicKey2())
				.withUseBlockchainSecureMessage(true)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithUseBlockchainSecureMessageAndRecipientPublicKey");
	}

	@Test
	public void shouldUploadWithUseBlockchainSecureMessageAndRecipientAddress() {
		final UploadParameter param = UploadParameter
				.createForStringUpload(
						StringParameterData.create(TEST_STRING, "UTF-8", "string description", "string name",
								"text/plain", singletonMap("keystring", "valstring")),
						IntegrationTestConfig.getPrivateKey1())
				.withRecipientAddress(IntegrationTestConfig.getAddress2())
				.withUseBlockchainSecureMessage(true)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithUseBlockchainSecureMessageAndRecipientAddress");
	}
}
