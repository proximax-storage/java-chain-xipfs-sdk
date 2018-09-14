package io.proximax.service.factory;

import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.PlainMessage;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.utils.JsonUtils;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The factory class to create the transaction message
 */
public class BlockchainMessageFactory {

    /**
     * Create a transaction message
     * @param messagePayload the message payload
     * @param signerPrivateKey the private key of signer
     * @param recipientPublicKey an optional recipient public key (if different from signer)
     * @param recipientAddress the optional address (if different from signer)
     * @param useBlockchainSecureMessage the flag to indicate if to create secure message
     * @return the created transaction message
     */
    public Message createMessage(ProximaxMessagePayloadModel messagePayload, String signerPrivateKey, String recipientPublicKey,
                                 String recipientAddress, boolean useBlockchainSecureMessage) {
        checkParameter(messagePayload != null, "messagePayload is required");

        // TODO handle secure message
        final String jsonPayload = JsonUtils.toJson(messagePayload);
        return PlainMessage.create(jsonPayload);
    }

}
