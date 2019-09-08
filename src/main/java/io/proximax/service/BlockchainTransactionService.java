package io.proximax.service;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.util.Collections.singletonList;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.GetTransactionFailureException;
import io.proximax.exceptions.TransactionNotAllowedException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.sdk.model.account.Address;
import io.proximax.sdk.model.mosaic.Mosaic;
import io.proximax.sdk.model.mosaic.NetworkCurrencyMosaic;
import io.proximax.sdk.model.transaction.Deadline;
import io.proximax.sdk.model.transaction.EntityType;
import io.proximax.sdk.model.transaction.Message;
import io.proximax.sdk.model.transaction.SignedTransaction;
import io.proximax.sdk.model.transaction.TransferTransaction;
import io.proximax.service.client.catapult.TransactionClient;
import io.proximax.utils.NemUtils;
import io.reactivex.Observable;

/**
 * The service class responsible for handling tasks that work with blockchain transactions
 */
public class BlockchainTransactionService {

    private final BlockchainNetworkConnection blockchainNetworkConnection;
    private final BlockchainMessageService blockchainMessageService;
    private final TransactionClient transactionClient;
    private final NemUtils nemUtils;

    /**
     * Construct service class
     *
     * @param blockchainNetworkConnection the config class to connect to blockchain network
     * @throws MalformedURLException when the blockchain endpoint URL fails
     */
    public BlockchainTransactionService(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.transactionClient = new TransactionClient(blockchainNetworkConnection);
        this.nemUtils = new NemUtils(blockchainNetworkConnection.getNetworkType());
        this.blockchainMessageService = new BlockchainMessageService(blockchainNetworkConnection);
    }

    BlockchainTransactionService(BlockchainNetworkConnection blockchainNetworkConnection, TransactionClient transactionClient,
                                 NemUtils nemUtils, BlockchainMessageService blockchainMessageService) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.transactionClient = transactionClient;
        this.nemUtils = nemUtils;
        this.blockchainMessageService = blockchainMessageService;
    }

    /**
     * Retrieves a transfer transaction
     *
     * @param transactionHash the transfer transaction hash
     * @return the transfer transaction
     */
    public Observable<TransferTransaction> getTransferTransaction(final String transactionHash) {
        checkParameter(transactionHash != null, "transactionHash is required");

        return transactionClient.getTransaction(transactionHash)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new GetTransactionFailureException(String.format("Unable to find transfer transaction for %s", transactionHash), ex)))
                .map(transaction -> {
                    if (!(transaction.getType().equals(EntityType.TRANSFER) &&
                            transaction instanceof TransferTransaction))
                        throw new TransactionNotAllowedException("Expecting a transfer transaction");
                    return (TransferTransaction) transaction;
                });
    }

    /**
     * Create and announce a blockchain transaction
     *
     * @param messagePayload             the message payload
     * @param signerPrivateKey           the signer's private key for the transaction
     * @param recipientPublicKey         the recipient's public key for the transaction (if different from signer)
     * @param recipientAddress           the recipient's address for the transaction (if different from signer)
     * @param transactionDeadline        the transaction deadline in hours
     * @param transactionMosaics         the mosaics to use on upload transaction
     * @param useBlockchainSecureMessage the flag to indicate if secure message will be created
     * @return the transaction hash
     */
    public Observable<String> createAndAnnounceTransaction(ProximaxMessagePayloadModel messagePayload, String signerPrivateKey,
                                                           String recipientPublicKey, String recipientAddress,
                                                           int transactionDeadline, List<Mosaic> transactionMosaics, boolean useBlockchainSecureMessage) {
        checkParameter(signerPrivateKey != null, "signerPrivateKey is required");
        checkParameter(messagePayload != null, "messagePayload is required");

        final Message message = blockchainMessageService.createMessage(messagePayload, signerPrivateKey,
                recipientPublicKey, recipientAddress, useBlockchainSecureMessage);
        final Address recipient = getRecipient(signerPrivateKey, recipientPublicKey, recipientAddress);
        final TransferTransaction transaction = createTransaction(recipient, transactionDeadline, transactionMosaics, message);
        final String networkGenerationHash = blockchainNetworkConnection.getBlockchainApi().getNetworkGenerationHash();
        final SignedTransaction signedTransaction = nemUtils.signTransaction(signerPrivateKey, transaction, networkGenerationHash);

        transactionClient.announce(signedTransaction, nemUtils.getAddressFromPrivateKey(signerPrivateKey));

        return Observable.just(signedTransaction.getHash());
    }

    private Address getRecipient(String signerPrivateKey, String recipientPublicKey, String recipientAddress) {
        if (recipientPublicKey != null) {
            return nemUtils.getAddressFromPublicKey(recipientPublicKey);
        } else if (recipientAddress != null) {
            return nemUtils.getAddress(recipientAddress);
        } else {
            return nemUtils.getAddressFromPrivateKey(signerPrivateKey);
        }
    }

    private TransferTransaction createTransaction(Address recipientAddress, int transactionDeadline,
         List<Mosaic> transactionMosaicsParam, Message message) {

      final List<Mosaic> mosaics = transactionMosaicsParam == null
            ? singletonList(NetworkCurrencyMosaic.createAbsolute(BigInteger.ONE))
            : transactionMosaicsParam;

      return blockchainNetworkConnection.getBlockchainApi().transact().transfer().mosaics(mosaics).to(recipientAddress)
            .message(message).deadline(Deadline.create(transactionDeadline, ChronoUnit.HOURS)).build();
   }
}
