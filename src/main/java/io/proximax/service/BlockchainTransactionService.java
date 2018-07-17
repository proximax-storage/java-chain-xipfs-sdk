package io.proximax.service;

import io.nem.sdk.model.mosaic.Mosaic;
import io.nem.sdk.model.mosaic.XEM;
import io.nem.sdk.model.transaction.Deadline;
import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.TransactionAnnounceResponse;
import io.nem.sdk.model.transaction.TransactionType;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.GetTransactionFailureException;
import io.proximax.exceptions.TransactionNotAllowedException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.client.TransactionClient;
import io.proximax.service.factory.BlockchainMessageFactory;
import io.proximax.utils.NemUtils;
import io.reactivex.Observable;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static com.google.common.base.Preconditions.checkArgument;

public class BlockchainTransactionService {

    private final BlockchainNetworkConnection blockchainNetworkConnection;
    private final BlockchainMessageFactory blockchainMessageFactory;
    private final TransactionClient transactionClient;
    private final NemUtils nemUtils;

    public BlockchainTransactionService(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.transactionClient = new TransactionClient(blockchainNetworkConnection);
        this.nemUtils = new NemUtils(blockchainNetworkConnection.getNetworkType());
        this.blockchainMessageFactory = new BlockchainMessageFactory();
    }

    BlockchainTransactionService(BlockchainNetworkConnection blockchainNetworkConnection, TransactionClient transactionClient,
                                 NemUtils nemUtils, BlockchainMessageFactory blockchainMessageFactory) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.transactionClient = transactionClient;
        this.nemUtils = nemUtils;
        this.blockchainMessageFactory = blockchainMessageFactory;
    }

    public Observable<TransferTransaction> getTransferTransaction(final String transactionHash) {
        checkArgument(transactionHash != null, "transactionHash is required");

        return transactionClient.getTransaction(transactionHash)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new GetTransactionFailureException(String.format("Unable to get transaction for %s", transactionHash), ex)))
                .map(transaction -> {
                    if (!(transaction.getType().equals(TransactionType.TRANSFER) &&
                            transaction instanceof TransferTransaction))
                        throw new TransactionNotAllowedException("Expecting a transfer transaction");
                    return (TransferTransaction) transaction;
                });
    }

    public Observable<String> createAndAnnounceTransaction(PrivacyStrategy privacyStrategy, String signerPrivateKey,
                                                           String recipientPublicKey, ProximaxMessagePayloadModel messagePayload) {
        checkArgument(privacyStrategy != null, "privacyStrategy is required");
        checkArgument(signerPrivateKey != null, "signerPrivateKey is required");
        checkArgument(recipientPublicKey != null, "recipientPublicKey is required");
        checkArgument(messagePayload != null, "messagePayload is required");

        final Message message = blockchainMessageFactory.createMessage(privacyStrategy, messagePayload);
        final TransferTransaction transaction = createTransaction(recipientPublicKey, message);
        final SignedTransaction signedTransaction = nemUtils.signTransaction(signerPrivateKey, transaction);
        return announce(signedTransaction)
                .map(response -> {
                    // TODO verify the announce response is successful
                    // /transaction/<txnhash>/status
                    // otherwise throw AnnounceBlockchainTransactionFailureException
                    return signedTransaction.getHash();
                });
    }

    private TransferTransaction createTransaction(String recipientPublicKey, Message message) {
        return TransferTransaction.create(
                Deadline.create(12, ChronoUnit.HOURS),
                nemUtils.toAddress(recipientPublicKey),
                Collections.singletonList(new Mosaic(XEM.createRelative(BigInteger.valueOf(1)).getId(), BigInteger.valueOf(1))),
                message,
                blockchainNetworkConnection.getNetworkType());
    }

    private Observable<TransactionAnnounceResponse> announce(SignedTransaction signedTransaction) {
        return transactionClient.announce(signedTransaction);
    }

}
