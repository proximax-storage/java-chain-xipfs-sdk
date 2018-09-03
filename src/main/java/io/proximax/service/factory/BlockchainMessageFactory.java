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
     * @return the created transaction message
     */
    public Message createMessage(ProximaxMessagePayloadModel messagePayload) {
        checkParameter(messagePayload != null, "messagePayload is required");

        final String jsonPayload = JsonUtils.toJson(messagePayload);
        return new PlainMessage(jsonPayload);
    }
}
