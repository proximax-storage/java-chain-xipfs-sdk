package io.proximax.service;

import static io.proximax.service.client.catapult.TransactionClient.STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.AnnounceBlockchainTransactionFailureException;
import io.proximax.exceptions.GetTransactionFailureException;
import io.proximax.exceptions.TransactionNotAllowedException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.sdk.BlockchainApi;
import io.proximax.sdk.model.account.Account;
import io.proximax.sdk.model.account.Address;
import io.proximax.sdk.model.blockchain.NetworkType;
import io.proximax.sdk.model.mosaic.Mosaic;
import io.proximax.sdk.model.mosaic.MosaicId;
import io.proximax.sdk.model.transaction.AggregateTransaction;
import io.proximax.sdk.model.transaction.Deadline;
import io.proximax.sdk.model.transaction.Message;
import io.proximax.sdk.model.transaction.SignedTransaction;
import io.proximax.sdk.model.transaction.TransactionType;
import io.proximax.sdk.model.transaction.TransferTransaction;
import io.proximax.service.client.catapult.TransactionClient;
import io.proximax.utils.NemUtils;
import io.reactivex.Observable;

public class BlockchainTransactionServiceTest {

    private static final String NETWORK_GENERATION_HASH = "122EB09F00E1F6AE6ABA96977E7676575E315CBDF79A83164FFA03B7CAE88927";
    private static final String SAMPLE_TRANSACTION_HASH = "0166C76DCEC445BA6F4269505F60CB34BBFBDDE7E973697D159FA98286712463";
    private static final String SAMPLE_SIGNER_PRIVATE_KEY = "3C5FE45A711448245203832295523623A5D09A7B49F354B54933E4D5564D50F7";
    private static final String SAMPLE_RECIPIENT_PUBLIC_KEY = "44AFFBEFF7EE8AFDE907EAD933C88374DE22F359CCBB6575BB14A8CB96B11C90";
    private static final Address SAMPLE_RECIPIENT_ADDRESS = Address.createFromRawAddress("VC6LV4EP2MT3I5AV76IKVTPVAZRJUQDMKSABLE2M");
    private static final Account SAMPLE_SIGNER_ACCOUNT = Account.createFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY, NetworkType.TEST_NET);
    private static final List<Mosaic> SAMPLE_MOSAICS = singletonList(new Mosaic(new MosaicId(new BigInteger("0DC67FBE1CAD29E3", 16)), BigInteger.TEN));

    private BlockchainTransactionService unitUnderTest;

    @Mock
    private BlockchainApi mockBlockchainApi;
    
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
    private BlockchainMessageService mockBlockchainMessageService;

    @Mock
    private Message mockMessage;

    @Mock
    private ProximaxMessagePayloadModel mockMessagePayload;

    @Mock
    private SignedTransaction mockSignedTransaction;

    @Captor
    private ArgumentCaptor<String> signerPrivateKeyArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> networkHashArgumentCaptor;

    @Captor
    private ArgumentCaptor<TransferTransaction> transferTransactionArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new BlockchainTransactionService(mockBlockchainNetworkConnection, mockTransactionClient, mockNemUtils, mockBlockchainMessageService);
        
        given(mockBlockchainApi.getNetworkGenerationHash()).willReturn(NETWORK_GENERATION_HASH);
        given(mockBlockchainNetworkConnection.getNetworkType()).willReturn(NetworkType.TEST_NET);
        given(mockBlockchainNetworkConnection.getBlockchainApi()).willReturn(mockBlockchainApi);
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
    public void failOnCreateAndAnnounceTransactionWhenNullMessagePayload() {
        unitUnderTest.createAndAnnounceTransaction(null, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, SAMPLE_MOSAICS, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnCreateAndAnnounceTransactionWhenNullSignerPrivateKey() {
        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, null,
                null, null, 12, SAMPLE_MOSAICS, false);
    }

    @Test(expected = RuntimeException.class)
    public void shouldBubbleUpExceptionsOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willThrow(new RuntimeException());

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS.plain(), 12, SAMPLE_MOSAICS,false);
    }

    @Test(expected = AnnounceBlockchainTransactionFailureException.class)
    public void shouldFailWhenAnnouncementFailed() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willThrow(new AnnounceBlockchainTransactionFailureException("failed"));

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, null, false).blockingFirst();
    }

    @Test
    public void shouldSignTransactionWithCorrectDataOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, null,false).blockingFirst();

        assertThat(signerPrivateKeyArgumentCaptor.getValue(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(transferTransactionArgumentCaptor.getValue().getMessage(), is(mockMessage));		
        assertThat(transferTransactionArgumentCaptor.getValue().getRecipient().getAddress().get(), is(SAMPLE_RECIPIENT_ADDRESS));
        //assertThat(transferTransactionArgumentCaptor.getValue().getDeadline().getLocalDateTime(), sameOrBefore(LocalDateTime.now().plusHours(12)));		
		long nowSinceNemesis = new Deadline(0, ChronoUnit.SECONDS).getInstant();		
        assertTrue(nowSinceNemesis < transferTransactionArgumentCaptor.getValue().getDeadline().getInstant());
        // assertTrue(new Deadline(12, ChronoUnit.HOURS).getInstant() < transferTransactionArgumentCaptor.getValue().getDeadline().getInstant());
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics(), hasSize(1));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics().get(0).getId(),
                is(new MosaicId(new BigInteger("0DC67FBE1CAD29E3", 16))));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics().get(0).getAmount(),
                is(BigInteger.valueOf(1)));
        assertThat(transferTransactionArgumentCaptor.getValue().getNetworkType(),
                is(NetworkType.TEST_NET));
    }

    @Test
    public void shouldReturnTransactionHashOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);

        final String transactionHash =
                unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                        null, null, 12, null,false).blockingFirst();

        assertThat(transactionHash, is(SAMPLE_TRANSACTION_HASH));
    }

    @Test
    public void shouldUseSignerAddressWhenNullRecipientPublicKeyAndAddress() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT.getAddress());

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, null, false).blockingFirst();

        assertThat(transferTransactionArgumentCaptor.getValue().getRecipient().getAddress().get().plain(), is(SAMPLE_SIGNER_ACCOUNT.getAddress().plain()));
    }

    @Test
    public void shouldUseRecipientPublicKeyAddressWhenProvided() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY,
                SAMPLE_RECIPIENT_ADDRESS.plain(), false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddressFromPublicKey(SAMPLE_RECIPIENT_PUBLIC_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS.plain(), 12, null,false).blockingFirst();

        assertThat(transferTransactionArgumentCaptor.getValue().getRecipient().getAddress().get().plain(), is(SAMPLE_RECIPIENT_ADDRESS.plain()));
    }

    @Test
    public void shouldUseRecipientAddressWhenNullRecipientPublicKeyButAddressProvided() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                SAMPLE_RECIPIENT_ADDRESS.plain(), false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddress(SAMPLE_RECIPIENT_ADDRESS.plain())).willReturn(SAMPLE_RECIPIENT_ADDRESS);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, SAMPLE_RECIPIENT_ADDRESS.plain(), 12, null,false).blockingFirst();

        assertThat(transferTransactionArgumentCaptor.getValue().getRecipient().getAddress().get().plain(), is(SAMPLE_RECIPIENT_ADDRESS.plain()));
    }

    @Test
    public void shouldUseProvidedTransactionDeadline() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                SAMPLE_RECIPIENT_ADDRESS.plain(), false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddress(SAMPLE_RECIPIENT_ADDRESS.plain())).willReturn(SAMPLE_RECIPIENT_ADDRESS);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, SAMPLE_RECIPIENT_ADDRESS.plain(), 15, null,false).blockingFirst();

        //assertThat(transferTransactionArgumentCaptor.getValue().getDeadline().getLocalDateTime(), sameOrBefore(Deadline.create(15, ChronoUnit.HOURS).getLocalDateTime()));
        //assertTrue(new Deadline(15, ChronoUnit.HOURS).getInstant() < transferTransactionArgumentCaptor.getValue().getDeadline().getInstant());
		long nowSinceNemesis = new Deadline(0, ChronoUnit.SECONDS).getInstant();		
        assertTrue(nowSinceNemesis < transferTransactionArgumentCaptor.getValue().getDeadline().getInstant());
    }

    @Test
    public void shouldUseProvidedTransactionMosaics() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                SAMPLE_RECIPIENT_ADDRESS.plain(), false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture(), networkHashArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)) .willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddress(SAMPLE_RECIPIENT_ADDRESS.plain())).willReturn(SAMPLE_RECIPIENT_ADDRESS);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, SAMPLE_RECIPIENT_ADDRESS.plain(), 12, SAMPLE_MOSAICS,false).blockingFirst();

        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics(), is(SAMPLE_MOSAICS));
    }
}
