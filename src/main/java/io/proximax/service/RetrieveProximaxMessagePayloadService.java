package io.proximax.service;

import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.account.PublicAccount;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.PlainMessage;
import io.nem.sdk.model.transaction.SecureMessage;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.DownloadForMessageTypeNotSupportedException;
import io.proximax.exceptions.InvalidPrivateKeyOnDownloadException;
import io.proximax.exceptions.MissingPrivateKeyOnDownloadException;
import io.proximax.exceptions.MissingSignerOnTransferTransactionException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.service.client.catapult.AccountClient;
import io.proximax.utils.JsonUtils;

import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving message payload from transaction
 */
public class RetrieveProximaxMessagePayloadService {

    private final NetworkType networkType;
    private final AccountClient accountClient;

    public RetrieveProximaxMessagePayloadService(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.accountClient = new AccountClient(blockchainNetworkConnection);
        this.networkType = blockchainNetworkConnection.getNetworkType();
    }

    RetrieveProximaxMessagePayloadService(NetworkType networkType, AccountClient accountClient) {
        this.networkType = networkType;
        this.accountClient = accountClient;
    }

    /**
     * Retrieves message payload from blockchain transaction
     * @param transferTransaction the blockchain transaction
     * @param accountPrivateKey the private key of either signer or recipient to read secure message
     * @return the message payload
     */
    public ProximaxMessagePayloadModel getMessagePayload(TransferTransaction transferTransaction, String accountPrivateKey) {
        checkParameter(transferTransaction != null, "transferTransaction is required");

        if (transferTransaction.getMessage() instanceof PlainMessage) {
            final String messagePayload = new String(transferTransaction.getMessage().getEncodedPayload(), Charset.forName("UTF-8"));
            return JsonUtils.fromJson(messagePayload, ProximaxMessagePayloadModel.class);
        } else if (transferTransaction.getMessage() instanceof SecureMessage) {
            if (accountPrivateKey == null)
                throw new MissingPrivateKeyOnDownloadException("accountPrivateKey is required to download a secure message");

            final KeyPair retrieverKeyPair = new KeyPair(PrivateKey.fromHexString(accountPrivateKey));
            final String messagePayload = new String(transferTransaction.getMessage().getDecodedPayload(
                    retrieverKeyPair, new KeyPair(getTransactionOtherPartyPublicKey(retrieverKeyPair, transferTransaction))
            ), Charset.forName("UTF-8"));

            return JsonUtils.fromJson(messagePayload, ProximaxMessagePayloadModel.class);
        } else {
            throw new DownloadForMessageTypeNotSupportedException(
                    String.format("Download of message type %s is not supported", transferTransaction.getMessage().getClass().getSimpleName()));
        }
    }

    private PublicKey getTransactionOtherPartyPublicKey(KeyPair retrieverKeyPair, TransferTransaction transferTransaction) {
        final PublicAccount senderAccount = Optional.of(transferTransaction).flatMap(Transaction::getSigner).orElseThrow(
                () -> new MissingSignerOnTransferTransactionException("Unexpected missing signer on transfer transaction"));
        final Address recipient = transferTransaction.getRecipient();
        final Address retrieverAddress = Address.createFromPublicKey(retrieverKeyPair.getPublicKey().toString(), networkType);

        if (retrieverAddress.equals(recipient)) { // retriever is the recipient, use sender public key
            return PublicKey.fromHexString(senderAccount.getPublicKey());
        } else if (retrieverAddress.equals(senderAccount.getAddress())){ // retriever is the sender, use recipient public key
            return accountClient.getPublicKey(recipient.plain());
        } else {
            throw new InvalidPrivateKeyOnDownloadException("accountPrivateKey cannot be used to read secure transaction message");
        }
    }
}