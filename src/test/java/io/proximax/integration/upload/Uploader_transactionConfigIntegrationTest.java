package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.ADDRESS_2;
import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_2;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static io.proximax.testsupport.TestHelper.logAndSaveResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_transactionConfigIntegrationTest {

	private Uploader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Uploader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldUploadWithRecipientPublicKeyProvided() throws Exception {
		final UploadParameter param = UploadParameter.createForStringUpload(STRING_TEST, PRIVATE_KEY_1)
				.withRecipientPublicKey(PRIVATE_KEY_2)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientPublicKeyProvided");
	}

	@Test
	public void shouldUploadWithRecipientAddressProvided() throws Exception {
		final UploadParameter param = UploadParameter.createForStringUpload(STRING_TEST, PRIVATE_KEY_1)
				.withRecipientAddress(ADDRESS_2)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientAddressProvided");
	}

	@Test
	public void shouldUploadWithTransactionDeadlinesProvided() throws Exception {
		final UploadParameter param = UploadParameter.createForStringUpload(STRING_TEST, PRIVATE_KEY_1)
				.withTransactionDeadline(2)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithTransactionDeadlinesProvided");
	}

	@Test
	public void shouldUploadWithUseBlockchainSecureMessageProvided() throws Exception {
		final UploadParameter param = UploadParameter.createForStringUpload(STRING_TEST, PRIVATE_KEY_1)
				.withUseBlockchainSecureMessage(true)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithUseBlockchainSecureMessageProvided");
	}

}
