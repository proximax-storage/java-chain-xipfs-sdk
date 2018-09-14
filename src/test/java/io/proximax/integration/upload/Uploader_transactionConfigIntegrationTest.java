package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.IntegrationTestProperties;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.TEST_STRING;
import static io.proximax.testsupport.TestDataRepository.logAndSaveResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_transactionConfigIntegrationTest {

	private Uploader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Uploader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestProperties.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestProperties.getIpfsMultiAddress())));
	}

	@Test
	public void shouldUploadWithRecipientPublicKeyProvided() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestProperties.getPrivateKey1())
				.withRecipientPublicKey(IntegrationTestProperties.getPublicKey2())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientPublicKeyProvided");
	}

	@Test
	public void shouldUploadWithRecipientAddressProvided() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestProperties.getPrivateKey1())
				.withRecipientAddress(IntegrationTestProperties.getAddress2())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientAddressProvided");
	}

	@Test
	public void shouldUploadWithTransactionDeadlinesProvided() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestProperties.getPrivateKey1())
				.withTransactionDeadline(2)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithTransactionDeadlinesProvided");
	}

	@Test
	public void shouldUploadWithUseBlockchainSecureMessageProvided() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestProperties.getPrivateKey1())
				.withUseBlockchainSecureMessage(true)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithUseBlockchainSecureMessageProvided");
	}

}
