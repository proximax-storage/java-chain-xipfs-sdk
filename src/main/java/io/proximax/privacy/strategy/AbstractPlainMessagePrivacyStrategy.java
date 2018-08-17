package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.PlainMessage;

/**
 * The abstract class implementation of Privacy Strategy for encoding and decoding of transaction message as plain message
 */
public abstract class AbstractPlainMessagePrivacyStrategy extends PrivacyStrategy {

    AbstractPlainMessagePrivacyStrategy(String privacySearchTag) {
        super(privacySearchTag);
    }

    /**
     * Create a plain message for transaction
     * @param payload the payload to be attached as message
     * @return the plain message
     */
    @Override
    public final Message encodePayloadAsMessage(String payload) {
        return PlainMessage.create(payload);
    }

    /**
     * Reads the payload from the plain message
     * @param message the transaction message
     * @return the payload
     */
    @Override
    public final String decodePayloadFromMessage(Message message) {
        return message.getPayload();
    }
}
