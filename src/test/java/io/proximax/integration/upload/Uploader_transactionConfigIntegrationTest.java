package io.proximax.integration.upload;

import io.proximax.sdk.infrastructure.Listener;
import io.proximax.sdk.model.account.Account;
import io.proximax.sdk.model.blockchain.NetworkType;
import io.proximax.sdk.model.mosaic.Mosaic;
import io.proximax.sdk.model.mosaic.MosaicId;
import io.proximax.sdk.model.transaction.Deadline;
import io.proximax.sdk.model.transaction.Transaction;
import io.proximax.sdk.model.transaction.TransactionInfo;
import io.proximax.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.UploadFailureException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.time.temporal.ChronoUnit;

import static io.proximax.integration.TestDataRepository.logAndSaveResult;
import static io.proximax.testsupport.Constants.TEST_STRING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;

public class Uploader_transactionConfigIntegrationTest {

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
    public void shouldUploadWithSignerAsRecipientByDefault() {
        final UploadParameter param = UploadParameter
                .createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
        assertThat(transaction, is(instanceOf(TransferTransaction.class)));
        assertThat(((TransferTransaction) transaction).getRecipient().getAddress().get().plain(), is(IntegrationTestConfig.getAddress1()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientPublicKeyProvided");
    }

    @Test(expected = UploadFailureException.class)
    public void failUploadWhenSignerHasNoFunds() {
        final UploadParameter param = UploadParameter
                .createForStringUpload(TEST_STRING, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                .build();

        unitUnderTest.upload(param);
    }

    @Test
    public void shouldUploadWithRecipientPublicKeyProvided() {
        final UploadParameter param = UploadParameter
                .createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
                .withRecipientPublicKey(IntegrationTestConfig.getPublicKey2())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
        assertThat(transaction, is(instanceOf(TransferTransaction.class)));
        assertThat(((TransferTransaction) transaction).getRecipient().getAddress().get().plain(), is(IntegrationTestConfig.getAddress2()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientPublicKeyProvided");
    }

    @Test
    public void shouldUploadWithRecipientAddressProvided() {
        final UploadParameter param = UploadParameter
                .createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
                .withRecipientAddress(IntegrationTestConfig.getAddress2())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
        assertThat(transaction, is(instanceOf(TransferTransaction.class)));
        assertThat(((TransferTransaction) transaction).getRecipient().getAddress().get().plain(), is(IntegrationTestConfig.getAddress2()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithRecipientAddressProvided");
    }

    @Test
    public void shouldUploadWithTransactionDeadlineProvided() {
        final UploadParameter param = UploadParameter
                .createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
                .withTransactionDeadline(5)
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
        assertThat(transaction, is(instanceOf(TransferTransaction.class)));
        assertTrue(new Deadline(5, ChronoUnit.HOURS).getInstant() < transaction.getDeadline().getInstant());
        //assertThat(transaction.getDeadline().getInstant(), sameOrBefore(Deadline.create(5, ChronoUnit.HOURS).getInstant()));
        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithTransactionDeadlineProvided");
    }

    @Test
    public void shouldUploadWithTransactionMosaicsProvided() {
        final UploadParameter param = UploadParameter
                .createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
                .withTransactionMosaic(singletonList(new Mosaic(new MosaicId(new BigInteger("0DC67FBE1CAD29E3", 16)), BigInteger.valueOf(2))))
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
        assertThat(transaction, is(instanceOf(TransferTransaction.class)));
        assertThat(((TransferTransaction) transaction).getMosaics().get(0).getId(), is(new MosaicId(new BigInteger("0DC67FBE1CAD29E3", 16))));
        assertThat(((TransferTransaction) transaction).getMosaics().get(0).getAmount(), is(BigInteger.valueOf(2)));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithTransactionMosaicsProvided");
    }

    @Test
    public void shouldUploadWithEmptyTransactionMosaicsProvided() {
        final UploadParameter param = UploadParameter
                .createForStringUpload(TEST_STRING, IntegrationTestConfig.getPrivateKey1())
                .withTransactionMosaic(emptyList())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        final Transaction transaction = waitForTransactionConfirmation(IntegrationTestConfig.getPrivateKey1(), result.getTransactionHash());
        assertThat(transaction, is(instanceOf(TransferTransaction.class)));
        assertThat(((TransferTransaction) transaction).getMosaics().size(), is(0));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadWithEmptyTransactionMosaicsProvided");
    }

    private Transaction waitForTransactionConfirmation(String senderPrivateKey, String transactionHash) {
        try {
            final Listener listener = (Listener) connectionConfig.getBlockchainNetworkConnection().getBlockchainApi().createListener();
            listener.open().get();
            final Transaction transaction = listener.confirmed(Account.createFromPrivateKey(senderPrivateKey, NetworkType.MIJIN_TEST).getAddress())
                    .filter(unconfirmedTxn
                            -> unconfirmedTxn.getTransactionInfo()
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
