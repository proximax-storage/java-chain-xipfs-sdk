/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.proximax.service;

import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.account.PublicAccount;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.Message;
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
 * The service class responsible for handling tasks that work with blockchain transaction messages
 */
public class BlockchainMessageService {

    private final NetworkType networkType;
    private final AccountClient accountClient;

    public BlockchainMessageService(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.accountClient = new AccountClient(blockchainNetworkConnection);
        this.networkType = blockchainNetworkConnection.getNetworkType();
    }

    BlockchainMessageService(NetworkType networkType, AccountClient accountClient) {
        this.networkType = networkType;
        this.accountClient = accountClient;
    }

    /**
     * Create a transaction message
     *
     * @param messagePayload             the message payload
     * @param senderPrivateKey           the private key of sender
     * @param recipientPublicKeyRaw      an optional recipient public key (if different from signer)
     * @param recipientAddress           the optional address (if different from signer)
     * @param useBlockchainSecureMessage the flag to indicate if to create secure message
     * @return the created transaction message
     */
    public Message createMessage(ProximaxMessagePayloadModel messagePayload, String senderPrivateKey, String recipientPublicKeyRaw,
                                 String recipientAddress, boolean useBlockchainSecureMessage) {
        checkParameter(messagePayload != null, "messagePayload is required");

        final String jsonPayload = JsonUtils.toJson(messagePayload);
        final byte[] payload = jsonPayload.getBytes(Charset.forName("UTF-8"));

        if (useBlockchainSecureMessage) {
            final PublicKey recipientPublicKey = getRecipientPublicKey(senderPrivateKey, recipientPublicKeyRaw, recipientAddress);
            final PrivateKey signerPrivateKey = PrivateKey.fromHexString(senderPrivateKey);
            return SecureMessage.createFromDecodedPayload(signerPrivateKey, recipientPublicKey, payload);
        } else {
            return PlainMessage.create(payload);
        }
    }

    /**
     * Retrieves message payload from blockchain transaction
     *
     * @param transferTransaction the blockchain transaction
     * @param accountPrivateKey   the private key of either signer or recipient to read secure message
     * @return the message payload
     */
    public String getMessagePayload(TransferTransaction transferTransaction, String accountPrivateKey) {
        checkParameter(transferTransaction != null, "transferTransaction is required");

        if (transferTransaction.getMessage() instanceof PlainMessage) {
            return new String(transferTransaction.getMessage().getEncodedPayload(), Charset.forName("UTF-8"));
        } else if (transferTransaction.getMessage() instanceof SecureMessage) {
            if (accountPrivateKey == null)
                throw new MissingPrivateKeyOnDownloadException("accountPrivateKey is required to download a secure message");

            final KeyPair retrieverKeyPair = new KeyPair(PrivateKey.fromHexString(accountPrivateKey));
            final String messagePayload = new String(transferTransaction.getMessage().getDecodedPayload(
                    retrieverKeyPair, new KeyPair(getTransactionOtherPartyPublicKey(retrieverKeyPair, transferTransaction))
            ), Charset.forName("UTF-8"));

            return messagePayload;
        } else {
            throw new DownloadForMessageTypeNotSupportedException(
                    String.format("Download of message type %s is not supported", transferTransaction.getMessage().getClass().getSimpleName()));
        }
    }

    private PublicKey getRecipientPublicKey(String senderPrivateKey, String recipientPublicKey, String recipientAddress) {
        if (recipientPublicKey != null) { // use public key if available
            return PublicKey.fromHexString(recipientPublicKey);
        } else if (recipientAddress != null) { // use public key from address
            final PublicKey senderPublicKey = new KeyPair(PrivateKey.fromHexString(senderPrivateKey)).getPublicKey();

            if (isSenderPrivateKeySameWithRecipientAddress(senderPublicKey, recipientAddress)) { // sending to self
                return senderPublicKey;
            } else { // sending to another
                return accountClient.getPublicKey(recipientAddress);
            }
        } else { // fallback to sender private key as default
            return new KeyPair(PrivateKey.fromHexString(senderPrivateKey)).getPublicKey();
        }
    }

    private boolean isSenderPrivateKeySameWithRecipientAddress(PublicKey signerPublicKey, String recipientAddress) {
        final Address senderAddress = Address.createFromPublicKey(signerPublicKey.toString(), networkType);
        return senderAddress.plain().equals(recipientAddress);
    }

    private PublicKey getTransactionOtherPartyPublicKey(KeyPair retrieverKeyPair, TransferTransaction transferTransaction) {
        final PublicAccount senderAccount = Optional.of(transferTransaction).flatMap(Transaction::getSigner).orElseThrow(
                () -> new MissingSignerOnTransferTransactionException("Unexpected missing signer on transfer transaction"));
        final Address recipient = transferTransaction.getRecipient();
        final Address retrieverAddress = Address.createFromPublicKey(retrieverKeyPair.getPublicKey().toString(), networkType);

        if (retrieverAddress.equals(recipient)) { // retriever is the recipient, use sender public key
            return PublicKey.fromHexString(senderAccount.getPublicKey());
        } else if (retrieverAddress.equals(senderAccount.getAddress())) { // retriever is the sender, use recipient public key
            return accountClient.getPublicKey(recipient.plain());
        } else {
            throw new InvalidPrivateKeyOnDownloadException("accountPrivateKey cannot be used to read secure transaction message");
        }
    }
}