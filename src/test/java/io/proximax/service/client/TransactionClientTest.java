package io.proximax.service.client;

import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class TransactionClientTest {

    private static final String SAMPLE_TRANSACTION_HASH = "F08E3C327DD5DE258EF20532F4D3C7638E9AC44885C34FDDC1A5740FD3C56EBB";

    private TransactionClient unitUnderTest;

    @Mock
    private TransactionHttp mockTransactionHttp;

    @Mock
    private SignedTransaction mockSignedTransaction;

    @Mock
    private TransactionAnnounceResponse mockTransactionAnnounceResponse;

    @Mock
    private Transaction mockTransaction;

    @Before
    public void setUp() throws MalformedURLException {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new TransactionClient(mockTransactionHttp);
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

}
