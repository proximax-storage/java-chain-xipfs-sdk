package io.proximax.service.factory;

import io.nem.sdk.model.transaction.Message;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.JsonUtils;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The factory class to create the transaction message
 */
public class BlockchainMessageFactory {

    /**
     * Create a transaction message
     * @param privacyStrategy the privacy strategy that handles the encoding of payload to message
     * @param messagePayload the message payload
     * @return the created transaction message
     */
    public Message createMessage(PrivacyStrategy privacyStrategy, ProximaxMessagePayloadModel messagePayload) {
        checkParameter(privacyStrategy != null, "privacyStrategy is required");
        checkParameter(messagePayload != null, "messagePayload is required");

        final String jsonPayload = JsonUtils.toJson(messagePayload);
        return privacyStrategy.encodePayloadAsMessage(jsonPayload);
    }
}
