package io.proximax.integration.upload;

import io.nem.sdk.infrastructure.Listener;
import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionInfo;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.TEST_STRING;
import static io.proximax.integration.TestDataRepository.logAndSaveResult;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_transactionConfigIntegrationTest {

	private Uploader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Uploader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestConfig.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestConfig.getIpfsMultiAddress())));
	}

	@Test
	public void shouldUploadWithSignerAsRecipientByDefault() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
		assertThat(transaction, is(instanceOf(TransferTransaction.class)));
		assertThat(((TransferTransaction)transaction).getRecipient().plain(), is(IntegrationTestConfig.getAddress1()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientPublicKeyProvided");
	}

	@Test
	public void shouldUploadWithRecipientPublicKeyProvided() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
				.withRecipientPublicKey(IntegrationTestConfig.getPublicKey2())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
		assertThat(transaction, is(instanceOf(TransferTransaction.class)));
		assertThat(((TransferTransaction)transaction).getRecipient().plain(), is(IntegrationTestConfig.getAddress2()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientPublicKeyProvided");
	}

	@Test
	public void shouldUploadWithRecipientAddressProvided() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
				.withRecipientAddress(IntegrationTestConfig.getAddress2())
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));
		final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
		assertThat(transaction, is(instanceOf(TransferTransaction.class)));
		assertThat(((TransferTransaction)transaction).getRecipient().plain(), is(IntegrationTestConfig.getAddress2()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientAddressProvided");
	}

	@Test
	public void shouldUploadWithTransactionDeadlinesProvided() throws Exception {
		final UploadParameter param = UploadParameter
				.createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
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
				.createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
				.withUseBlockchainSecureMessage(true)
				.build();

		final UploadResult result = unitUnderTest.upload(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(notNullValue()));

		logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithUseBlockchainSecureMessageProvided");
	}

	private Transaction waitForTransactionConfirmation(String senderPrivateKey, String transactionHash) {
		try {
			final Listener listener = new Listener(IntegrationTestConfig.getBlockchainRestUrl());
			listener.open().get();
			final Transaction transaction = listener.confirmed(Account.createFromPrivateKey(senderPrivateKey, NetworkType.MIJIN_TEST).getAddress())
					.filter(unconfirmedTxn ->
							unconfirmedTxn.getTransactionInfo()
									.flatMap(TransactionInfo::getHash)
									.map(hash -> hash.equals(transactionHash))
									.orElse(false)).blockingFirst();
			listener.close();
			return transaction;
		} catch (Exception e) {
			throw new RuntimeException("Failed to listen on confirmed transaction", e);
		}
	}
}
