package io.proximax.service.client;

import io.nem.sdk.infrastructure.Listener;
import io.nem.sdk.infrastructure.TransactionHttp;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.nem.sdk.model.transaction.TransactionInfo;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.AnnounceBlockchainTransactionFailureException;
import io.reactivex.Observable;

import java.net.MalformedURLException;

import static com.google.common.base.Preconditions.checkArgument;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

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

    public static final String STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION = "SUCCESS";
    private final TransactionHttp transactionHttp;
    private final String blockchainNetworkEndpointUrl;
    private final Listener listener;

    /**
     * Construct the class with BlockchainNetworkConnection
     * @param blockchainNetworkConnection the blockchain network connection
     * @throws MalformedURLException when the blockchain endpoint url is not valid
     */
    public TransactionClient(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        checkArgument(blockchainNetworkConnection != null, "blockchainNetworkConnection is required");

        this.transactionHttp = new TransactionHttp(blockchainNetworkConnection.getEndpointUrl());
        this.blockchainNetworkEndpointUrl = blockchainNetworkConnection.getEndpointUrl();
        this.listener = null;
    }

    TransactionClient(TransactionHttp transactionHttp, Listener listener) {
        this.transactionHttp = transactionHttp;
        this.blockchainNetworkEndpointUrl = null;
        this.listener = listener;
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
        checkParameter(signedTransaction != null, "signedTransaction is required");

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
        checkParameter(transactionHash != null, "transactionHash is required");

        return transactionHttp.getTransaction(transactionHash);
    }

    /**
     * Wait for announced transaction to become 'unconfirmed' status
     * @param address the address involved in the transaction
     * @param transactionHash the transaction to wait for
     * @return the status
     * @throws MalformedURLException when blockchain network endpoint URL is not correct
     */
    public synchronized String waitForAnnouncedTransactionToBeUnconfirmed(Address address, String transactionHash) throws MalformedURLException {
        checkParameter(address != null, "address is required");
        checkParameter(transactionHash != null, "transactionHash is required");

        final Listener listener = getListener();
        try {
            listener.open().get();
            final Observable<String> failedTransactionStatusOb =
                    getAddedFailedTransactionStatus(address, transactionHash, listener);
            final Observable<String> unconfirmedTransactionStatusOb =
                    getAddedUnconfirmedTransactionStatus(address, transactionHash, listener);

            return failedTransactionStatusOb.mergeWith(unconfirmedTransactionStatusOb)
                    .map(status -> {
                        if (status.equals(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION))
                            return status;
                        else
                            throw new AnnounceBlockchainTransactionFailureException(
                                    String.format("Failed to announce transaction with status %s", status));
                    }).blockingFirst();
        } catch (AnnounceBlockchainTransactionFailureException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AnnounceBlockchainTransactionFailureException("Failed to announce transaction", ex);
        } finally {
            listener.close();
        }
    }

    private Listener getListener() throws MalformedURLException {
        return listener != null ? listener : new Listener(blockchainNetworkEndpointUrl);
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

}
