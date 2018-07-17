package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.PlainMessage;

public abstract class AbstractPlainMessagePrivacyStrategy extends PrivacyStrategy {

    public AbstractPlainMessagePrivacyStrategy(String privacySearchTag) {
        super(privacySearchTag);
    }

    @Override
    public final Message encodePayloadAsMessage(String payload) {
        return PlainMessage.create(payload);
    }

    @Override
    public final String decodePayloadFromMessage(Message message) {
        return message.getPayload();
    }
}
