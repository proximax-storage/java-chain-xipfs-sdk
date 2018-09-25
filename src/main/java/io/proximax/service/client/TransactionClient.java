package io.proximax.service.client;

import io.nem.sdk.infrastructure.Listener;
import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionInfo;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.AnnounceBlockchainTransactionFailureException;
import io.reactivex.Observable;

import java.net.MalformedURLException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The client class that directly interface with the blockchain's transaction APIs
 * <br>
 * <br>
 * This class delegates to blockchain the following:
 * <ul>
 * <li>retrieval of transaction given a transaction hash</li>
 * <li>Synchronously announce a signed transaction</li>
 * </ul>
 */
public class TransactionClient {

    public static final String STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION = "SUCCESS";
    private final TransactionHttp transactionHttp;
    private final String blockchainNetworkRestApiUrl;
    private final Listener listener;

    /**
     * Construct the class with BlockchainNetworkConnection
     *
     * @param blockchainNetworkConnection the blockchain network connection
     * @throws MalformedURLException when the blockchain endpoint url is not valid
     */
    public TransactionClient(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        checkParameter(blockchainNetworkConnection != null, "blockchainNetworkConnection is required");

        this.transactionHttp = new TransactionHttp(blockchainNetworkConnection.getApiUrl());
        this.blockchainNetworkRestApiUrl = blockchainNetworkConnection.getApiUrl();
        this.listener = null;
    }

    TransactionClient(TransactionHttp transactionHttp, Listener listener) {
        this.transactionHttp = transactionHttp;
        this.blockchainNetworkRestApiUrl = null;
        this.listener = listener;
    }

    /**
     * Synchronously announce a signed transaction to blockchain
     * <br>
     * <br>
     * This method is equivalent to calling `PUT /transaction`
     *
     * @param signedTransaction the signed transaction
     * @param address the signer's address
     * @return the transaction announce result
     */
    public synchronized String announce(SignedTransaction signedTransaction, Address address) {
        checkParameter(signedTransaction != null, "signedTransaction is required");
        checkParameter(address != null, "address is required");

        final Listener listener = getListener();
        try {
            listener.open().get(10, TimeUnit.SECONDS);
            final Observable<String> failedTransactionStatusOb =
                    getAddedFailedTransactionStatus(address, signedTransaction.getHash(), listener);
            final Observable<String> unconfirmedTransactionStatusOb =
                    getAddedUnconfirmedTransactionStatus(address, signedTransaction.getHash(), listener);

            final Future<String> statusFuture = failedTransactionStatusOb.mergeWith(unconfirmedTransactionStatusOb)
                    .map(status -> {
                        if (status.equals(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION))
                            return status;
                        else
                            throw new AnnounceBlockchainTransactionFailureException(
                                    String.format("Failed to announce transaction with status %s", status));
                    }).take(1).toFuture();

            transactionHttp.announce(signedTransaction).blockingFirst();
            return statusFuture.get(30, TimeUnit.SECONDS);
        } catch (AnnounceBlockchainTransactionFailureException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AnnounceBlockchainTransactionFailureException("Failed to announce transaction", ex);
        } finally {
            closeListener(listener);
        }
    }

    /**
     * Retrieves a transaction from blockchain
     * <br>
     * <br>
     * This method is equivalent to calling `GET /transaction/{transactionHash}`
     *
     * @param transactionHash the signed transaction
     * @return the transaction announce response
     */
    public Observable<Transaction> getTransaction(String transactionHash) {
        checkParameter(transactionHash != null, "transactionHash is required");

        return transactionHttp.getTransaction(transactionHash);
    }

    private Listener getListener() {
        try {
            return listener != null ? listener : new Listener(blockchainNetworkRestApiUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unexpected malformed URL", e);
        }
    }

    private Observable<String> getAddedUnconfirmedTransactionStatus(Address address, String transactionHash, Listener listener) {
        return listener.unconfirmedAdded(address)
                .filter(unconfirmedTxn ->
                        unconfirmedTxn.getTransactionInfo()
                                .flatMap(TransactionInfo::getHash)
                                .map(hash -> hash.equals(transactionHash))
                                .orElse(false))
                .map(unconfirmedTxn -> STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
    }

    private Observable<String> getAddedFailedTransactionStatus(Address address, String transactionHash, Listener listener) {
        return listener.status(address)
                .filter(transactionStatusError ->
                        transactionStatusError.getHash().equals(transactionHash))
                .map(transactionStatusError -> transactionStatusError.getStatus());
    }

    private void closeListener(Listener listener) {
        try {
            listener.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}
