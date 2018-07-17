package io.proximax.service;

import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.mosaic.XEM;
import io.nem.sdk.model.transaction.AggregateTransaction;
import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.nem.sdk.model.transaction.TransactionType;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.AnnounceBlockchainTransactionFailureException;
import io.proximax.exceptions.GetTransactionFailureException;
import io.proximax.exceptions.TransactionNotAllowedException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.client.TransactionClient;
import io.proximax.service.factory.BlockchainMessageFactory;
import io.proximax.utils.NemUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class BlockchainTransactionServiceTest {

    private static final String SAMPLE_TRANSACTION_HASH = "F08E3C327DD5DE258EF20532F4D3C7638E9AC44885C34FDDC1A5740FD3C56EBB";
    private static final String SAMPLE_SIGNER_PRIVATE_KEY = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
    private static final String SAMPLE_RECIPIENT_PUBLIC_KEY = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
    private static final Address SAMPLE_RECIPIENT_ADDRESS = Address.createFromRawAddress("SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F");

    private BlockchainTransactionService unitUnderTest;

    @Mock
    private BlockchainNetworkConnection mockBlockchainNetworkConnection;

    @Mock
    private TransactionClient mockTransactionClient;

    @Mock
    private AggregateTransaction mockAggregateTransaction;

    @Mock
    private TransferTransaction mockTransferTransaction;

    @Mock
    private NemUtils mockNemUtils;

    @Mock
    private BlockchainMessageFactory mockBlockchainMessageFactory;

    @Mock
    private Message mockMessage;

    @Mock
    private ProximaxMessagePayloadModel mockMessagePayload;

    @Mock
    private SignedTransaction mockSignedTransaction;

    @Mock
    private TransactionAnnounceResponse mockTransactionAnnounceResponse;

    @Mock
    private PrivacyStrategy mockPrivacyStrategy;

    @Captor
    private ArgumentCaptor<String> signerPrivateKeyArgumentCaptor;

    @Captor
    private ArgumentCaptor<TransferTransaction> transferTransactionArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new BlockchainTransactionService(mockBlockchainNetworkConnection, mockTransactionClient, mockNemUtils, mockBlockchainMessageFactory);

        given(mockBlockchainNetworkConnection.getNetworkType()).willReturn(NetworkType.MIJIN_TEST);
        given(mockTransferTransaction.getType()).willReturn(TransactionType.TRANSFER);
        given(mockAggregateTransaction.getType()).willReturn(TransactionType.AGGREGATE_COMPLETE);
        given(mockSignedTransaction.getHash()).willReturn(SAMPLE_TRANSACTION_HASH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetTransferTransactionWhenNullTransactionHash() {
        unitUnderTest.getTransferTransaction(null);
    }

    @Test(expected = GetTransactionFailureException.class)
    public void failOnGetTransferTransactionWhenInvalidTransactionHash() {
        given(mockTransactionClient.getTransaction(SAMPLE_TRANSACTION_HASH))
                .willReturn(Observable.error(new RuntimeException()));

        unitUnderTest.getTransferTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();
    }

    @Test(expected = TransactionNotAllowedException.class)
    public void failOnGetTransferTransactionWhenNotTransferTransaction() {
        given(mockTransactionClient.getTransaction(SAMPLE_TRANSACTION_HASH))
                .willReturn(Observable.just(mockAggregateTransaction));

        unitUnderTest.getTransferTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();
    }

    @Test
    public void shouldReturnTransferTransactionOnGetTransferTransaction() {
        given(mockTransactionClient.getTransaction(SAMPLE_TRANSACTION_HASH))
                .willReturn(Observable.just(mockTransferTransaction));

        final TransferTransaction transferTransaction = unitUnderTest.getTransferTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();

        assertThat(transferTransaction, is(mockTransferTransaction));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnCreateAndAnnounceTransactionWhenNullPrivacyStrategy() {
        unitUnderTest.createAndAnnounceTransaction(null, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY, mockMessagePayload);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnCreateAndAnnounceTransactionWhenNullSignerPrivateKey() {
        unitUnderTest.createAndAnnounceTransaction(mockPrivacyStrategy, null, SAMPLE_RECIPIENT_PUBLIC_KEY, mockMessagePayload);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnCreateAndAnnounceTransactionWhenNullRecipientPublicKey() {
        unitUnderTest.createAndAnnounceTransaction(mockPrivacyStrategy, SAMPLE_SIGNER_PRIVATE_KEY, null, mockMessagePayload);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnCreateAndAnnounceTransactionWhenNullMessagePayload() {
        unitUnderTest.createAndAnnounceTransaction(mockPrivacyStrategy, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY, null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldBubbleUpExceptionsOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageFactory.createMessage(mockPrivacyStrategy, mockMessagePayload)).willReturn(mockMessage);
        given(mockNemUtils.toAddress(any())).willThrow(new RuntimeException());

        unitUnderTest.createAndAnnounceTransaction(mockPrivacyStrategy, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY, mockMessagePayload);
    }

    @Ignore
    @Test(expected = AnnounceBlockchainTransactionFailureException.class)
    public void shouldFailWhenAnnouncementFailed() {
        final TransactionAnnounceResponse announceFailureResponse = new TransactionAnnounceResponse("BAD");
        given(mockTransactionClient.announce(mockSignedTransaction)).willReturn(Observable.just(announceFailureResponse));

        unitUnderTest.createAndAnnounceTransaction(mockPrivacyStrategy, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY, mockMessagePayload);
    }

    @Test
    public void shouldSignTransactionWithCorrectDataOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageFactory.createMessage(mockPrivacyStrategy, mockMessagePayload)).willReturn(mockMessage);
        given(mockNemUtils.toAddress(SAMPLE_RECIPIENT_PUBLIC_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction)).willReturn(Observable.just(mockTransactionAnnounceResponse));

        final String transactionHash =
                unitUnderTest.createAndAnnounceTransaction(mockPrivacyStrategy, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY, mockMessagePayload)
                        .blockingFirst();

        assertThat(signerPrivateKeyArgumentCaptor.getValue(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(transferTransactionArgumentCaptor.getValue().getMessage(), is(mockMessage));
        assertThat(transferTransactionArgumentCaptor.getValue().getRecipient(), is(SAMPLE_RECIPIENT_ADDRESS));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics(), hasSize(1));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics().get(0).getId(),
                is(XEM.createRelative(BigInteger.valueOf(1)).getId()));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics().get(0).getAmount(),
                is(BigInteger.valueOf(1)));
    }

    @Test
    public void shouldReturnTransactionHashOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageFactory.createMessage(mockPrivacyStrategy, mockMessagePayload)).willReturn(mockMessage);
        given(mockNemUtils.toAddress(SAMPLE_RECIPIENT_PUBLIC_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction)).willReturn(Observable.just(mockTransactionAnnounceResponse));

        final String transactionHash =
                unitUnderTest.createAndAnnounceTransaction(mockPrivacyStrategy, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY, mockMessagePayload).blockingFirst();

        assertThat(transactionHash, is(SAMPLE_TRANSACTION_HASH));
    }
}
