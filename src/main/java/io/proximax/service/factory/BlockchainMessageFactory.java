package io.proximax.service.factory;

import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.PlainMessage;
import io.nem.sdk.model.transaction.SecureMessage;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.service.client.catapult.AccountClient;
import io.proximax.utils.JsonUtils;

import java.net.MalformedURLException;
import java.nio.charset.Charset;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The factory class to create the transaction message
 */
public class BlockchainMessageFactory {

    private final NetworkType networkType;
    private final AccountClient accountClient;

    public BlockchainMessageFactory(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.accountClient = new AccountClient(blockchainNetworkConnection);
        this.networkType = blockchainNetworkConnection.getNetworkType();
    }

    BlockchainMessageFactory(NetworkType networkType, AccountClient accountClient) {
        this.networkType = networkType;
        this.accountClient = accountClient;
    }

    /**
     * Create a transaction message
     * @param messagePayload the message payload
     * @param senderPrivateKey the private key of sender
     * @param recipientPublicKeyRaw an optional recipient public key (if different from signer)
     * @param recipientAddress the optional address (if different from signer)
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
}
