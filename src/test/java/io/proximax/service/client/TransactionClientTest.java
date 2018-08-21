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

import java.io.IOException;
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
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnAnnounceWhenNullSignedTransaction() {
        unitUnderTest.announce(null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldBubbleUpExceptionOnAnnounce() throws IOException {
        given(mockTransactionHttp.announce(any())).willThrow(new RuntimeException());

        unitUnderTest.announce(mockSignedTransaction).blockingFirst();
    }

    @Test
    public void shouldReturnTransactionAnnounceResponseOnAnnounce() {
        given(mockTransactionHttp.announce(any())).willReturn(Observable.just(mockTransactionAnnounceResponse));

        final TransactionAnnounceResponse transactionAnnounceResponse =
                unitUnderTest.announce(mockSignedTransaction).blockingFirst();

        assertThat(transactionAnnounceResponse, is(mockTransactionAnnounceResponse));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetTransactionWhenNullTransactionHash() {
        unitUnderTest.getTransaction(null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldBubbleUpExceptionOnGetTransaction() throws IOException {
        given(mockTransactionHttp.getTransaction(SAMPLE_TRANSACTION_HASH)).willThrow(new RuntimeException());

        unitUnderTest.getTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();
    }

    @Test
    public void shouldReturnTransactionOnGetTransaction() {
        given(mockTransactionHttp.getTransaction(SAMPLE_TRANSACTION_HASH)).willReturn(Observable.just(mockTransaction));

        final Transaction transaction = unitUnderTest.getTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();

        assertThat(transaction, is(mockTransaction));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnWaitForAnnouncedTransactionToBeUnconfirmedWhenNullAddress() throws Exception {
        unitUnderTest.waitForAnnouncedTransactionToBeUnconfirmed(null, SAMPLE_TRANSACTION_HASH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnWaitForAnnouncedTransactionToBeUnconfirmedWhenNullTransactionHash() throws Exception {
        unitUnderTest.waitForAnnouncedTransactionToBeUnconfirmed(SAMPLE_ADDRESS, null);
    }

    @Test
    public void shouldReturnStatusOnWaitForAnnouncedTransactionToBeUnconfirmed() throws Exception {
        final CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        completableFuture.complete(null);
        given(mockListener.open()).willReturn(completableFuture);
        given(mockListener.status(SAMPLE_ADDRESS)).willReturn(Observable.empty());
        given(mockListener.unconfirmedAdded(SAMPLE_ADDRESS)).willReturn(Observable.just(mockTransaction));
        given(mockTransaction.getTransactionInfo()).willReturn(Optional.of(
                TransactionInfo.create(BigInteger.ONE, SAMPLE_TRANSACTION_HASH, "blahblah")));

        final String status = unitUnderTest.waitForAnnouncedTransactionToBeUnconfirmed(SAMPLE_ADDRESS, SAMPLE_TRANSACTION_HASH);

        assertThat(status, is(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION));
    }

    @Test(expected = AnnounceBlockchainTransactionFailureException.class)
    public void failOnWaitForAnnouncedTransactionToBeUnconfirmedWhenHasTransactionError() throws Exception {
        final CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        completableFuture.complete(null);
        given(mockListener.open()).willReturn(completableFuture);
        given(mockListener.status(SAMPLE_ADDRESS)).willReturn(Observable.just(
                new TransactionStatusError(SAMPLE_TRANSACTION_HASH, "Failure_Core_Insufficient_Balance", null)));
        given(mockListener.unconfirmedAdded(SAMPLE_ADDRESS)).willReturn(Observable.just(mockTransaction));
        given(mockTransaction.getTransactionInfo()).willReturn(Optional.of(
                TransactionInfo.create(BigInteger.ONE, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "blahblah")));
        unitUnderTest.waitForAnnouncedTransactionToBeUnconfirmed(SAMPLE_ADDRESS, SAMPLE_TRANSACTION_HASH);
    }

    @Test
    public void shouldIgnoreFailuresFromOtherTransactions() throws Exception {
        final CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        completableFuture.complete(null);
        given(mockListener.open()).willReturn(completableFuture);
        given(mockListener.status(SAMPLE_ADDRESS)).willReturn(Observable.just(
                new TransactionStatusError("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Failure_Core_Insufficient_Balance", null)));
        given(mockListener.unconfirmedAdded(SAMPLE_ADDRESS)).willReturn(Observable.just(mockTransaction));
        given(mockTransaction.getTransactionInfo()).willReturn(Optional.of(
                TransactionInfo.create(BigInteger.ONE, SAMPLE_TRANSACTION_HASH, "blahblah")));

        final String status = unitUnderTest.waitForAnnouncedTransactionToBeUnconfirmed(SAMPLE_ADDRESS, SAMPLE_TRANSACTION_HASH);

        assertThat(status, is(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION));
    }

}
