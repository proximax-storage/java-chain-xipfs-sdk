package io.proximax.service.client;

import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.proximax.connection.BlockchainNetworkConnection;
import io.reactivex.Observable;

import java.net.MalformedURLException;

import static com.google.common.base.Preconditions.checkArgument;

public class TransactionClient {

    private final TransactionHttp transactionHttp;

    public TransactionClient(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.transactionHttp = new TransactionHttp(blockchainNetworkConnection.getEndpointUrl());
    }

    TransactionClient(TransactionHttp transactionHttp) {
        this.transactionHttp = transactionHttp;
    }

    public Observable<TransactionAnnounceResponse> announce(SignedTransaction signedTransaction) {
        checkArgument(signedTransaction != null, "signedTransaction is required");

        return transactionHttp.announce(signedTransaction);
    }

    public Observable<Transaction> getTransaction(String transactionHash) {
        checkArgument(transactionHash != null, "transactionHash is required");

        return transactionHttp.getTransaction(transactionHash);
    }
}
