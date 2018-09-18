package io.proximax.service.client;

import io.nem.sdk.infrastructure.Listener;
import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.nem.sdk.model.transaction.TransactionInfo;
import io.nem.sdk.model.transaction.TransactionStatusError;
import io.proximax.exceptions.AnnounceBlockchainTransactionFailureException;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static io.proximax.service.client.TransactionClient.STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class TransactionClientTest {

    private static final String SAMPLE_TRANSACTION_HASH = "F08E3C327DD5DE258EF20532F4D3C7638E9AC44885C34FDDC1A5740FD3C56EBB";
    private static final Address SAMPLE_ADDRESS = Address.createFromRawAddress("SDROED2EKLFO3WNGK2VADE7QVENDZBK5JUKNAGME");

    private TransactionClient unitUnderTest;

    @Mock
    private TransactionHttp mockTransactionHttp;

    @Mock
    private Listener mockListener;

    @Mock
    private SignedTransaction mockSignedTransaction;

    @Mock
    private TransactionAnnounceResponse mockTransactionAnnounceResponse;

    @Mock
    private Transaction mockTransaction;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new TransactionClient(mockTransactionHttp, mockListener);

        given(mockSignedTransaction.getHash()).willReturn(SAMPLE_TRANSACTION_HASH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnAnnounceWhenNullSignedTransaction() {
        unitUnderTest.announce(null, SAMPLE_ADDRESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnAnnounceWhenNullAddress() {
        unitUnderTest.announce(mockSignedTransaction, null);
    }

    @Test
    public void shouldReturnSuccessTransactionStatusOnAnnounce() {
        given(mockTransactionHttp.announce(any())).willReturn(Observable.just(mockTransactionAnnounceResponse));
        given(mockListener.open()).willReturn(completableFuture(null));
        given(mockListener.status(SAMPLE_ADDRESS)).willReturn(Observable.empty());
        given(mockListener.unconfirmedAdded(SAMPLE_ADDRESS)).willReturn(Observable.just(mockTransaction));
        given(mockTransaction.getTransactionInfo()).willReturn(Optional.of(
                TransactionInfo.create(BigInteger.ONE, SAMPLE_TRANSACTION_HASH, "blahblah")));

        final String transactionStatus = unitUnderTest.announce(mockSignedTransaction, SAMPLE_ADDRESS);

        assertThat(transactionStatus, is(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION));
    }

    @Test(expected = AnnounceBlockchainTransactionFailureException.class)
    public void failOnWaitForAnnouncedTransactionToBeUnconfirmedWhenHasTransactionError() throws Exception {
        given(mockTransactionHttp.announce(any())).willReturn(Observable.just(mockTransactionAnnounceResponse));
        given(mockListener.open()).willReturn(completableFuture(null));
        given(mockListener.status(SAMPLE_ADDRESS)).willReturn(Observable.just(
                new TransactionStatusError(SAMPLE_TRANSACTION_HASH, "Failure_Core_Insufficient_Balance", null)));
        given(mockListener.unconfirmedAdded(SAMPLE_ADDRESS)).willReturn(Observable.just(mockTransaction));
        given(mockTransaction.getTransactionInfo()).willReturn(Optional.of(
                TransactionInfo.create(BigInteger.ONE, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "blahblah")));

        unitUnderTest.announce(mockSignedTransaction, SAMPLE_ADDRESS);
    }

    @Test
    public void shouldIgnoreFailuresFromOtherTransactions() throws Exception {
        given(mockTransactionHttp.announce(any())).willReturn(Observable.just(mockTransactionAnnounceResponse));
        given(mockListener.open()).willReturn(completableFuture(null));
        given(mockListener.status(SAMPLE_ADDRESS)).willReturn(Observable.just(
                new TransactionStatusError("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Failure_Core_Insufficient_Balance", null)));
        given(mockListener.unconfirmedAdded(SAMPLE_ADDRESS)).willReturn(Observable.just(mockTransaction));
        given(mockTransaction.getTransactionInfo()).willReturn(Optional.of(
                TransactionInfo.create(BigInteger.ONE, SAMPLE_TRANSACTION_HASH, "blahblah")));

        final String transactionStatus = unitUnderTest.announce(mockSignedTransaction, SAMPLE_ADDRESS);

        assertThat(transactionStatus, is(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetTransactionWhenNullTransactionHash() {
        unitUnderTest.getTransaction(null);
    }

    @Test
    public void shouldReturnTransactionOnGetTransaction() {
        given(mockTransactionHttp.getTransaction(SAMPLE_TRANSACTION_HASH)).willReturn(Observable.just(mockTransaction));

        final Transaction transaction = unitUnderTest.getTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();

        assertThat(transaction, is(mockTransaction));
    }

    private <T> CompletableFuture<T> completableFuture(T val) {
        final CompletableFuture<T> completableFuture = new CompletableFuture<>();
        completableFuture.complete(val);
        return completableFuture;
    }

}
