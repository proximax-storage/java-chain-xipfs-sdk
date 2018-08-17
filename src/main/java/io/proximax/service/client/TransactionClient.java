package io.proximax.service.client;

import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.proximax.connection.BlockchainNetworkConnection;
import io.reactivex.Observable;

import java.net.MalformedURLException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The client class that directly interface with the blockchain's transaction APIs
 * <br>
 * <br>
 * This class delegates to blockchain the following:
 * <ul>
 *     <li>retrieval of transaction given a transaction hash</li>
 *     <li>asynchronously announce a signed transaction</li>
 * </ul>
 */
public class TransactionClient {

    private final TransactionHttp transactionHttp;

    /**
     * Construct the class with BlockchainNetworkConnection
     * @param blockchainNetworkConnection the blockchain network connection
     * @throws MalformedURLException when the blockchain endpoint url is not valid
     */
    public TransactionClient(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        checkArgument(blockchainNetworkConnection != null, "blockchainNetworkConnection is required");

        this.transactionHttp = new TransactionHttp(blockchainNetworkConnection.getEndpointUrl());
    }

    TransactionClient(TransactionHttp transactionHttp) {
        this.transactionHttp = transactionHttp;
    }

    /**
     * Asynchronously announce a signed transaction to blockchain
     * <br>
     * <br>
     * This method is equivalent to calling `PUT /transaction`
     * @param signedTransaction the signed transaction
     * @return the transaction announce response
     */
    public Observable<TransactionAnnounceResponse> announce(SignedTransaction signedTransaction) {
        checkArgument(signedTransaction != null, "signedTransaction is required");

        return transactionHttp.announce(signedTransaction);
    }

    /**
     * Retrieves a transaction from blockchain
     * <br>
     * <br>
     * This method is equivalent to calling `GET /transaction/{transactionHash}`
     * @param transactionHash the signed transaction
     * @return the transaction announce response
     */
    public Observable<Transaction> getTransaction(String transactionHash) {
        checkArgument(transactionHash != null, "transactionHash is required");

        return transactionHttp.getTransaction(transactionHash);
    }
}
