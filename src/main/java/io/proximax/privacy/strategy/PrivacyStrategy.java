package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.Message;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public abstract class PrivacyStrategy {

    private final String privacySearchTag;

    public PrivacyStrategy(String privacySearchTag) {
        checkParameter(privacySearchTag == null || privacySearchTag.length() <= 50,
                "privacy search tag cannot be more than 50 characters");

        this.privacySearchTag = privacySearchTag;
    }

    public final String getPrivacySearchTag() {
        return privacySearchTag;
    }

    public abstract int getPrivacyType();

    public abstract byte[] encryptData(final byte[] data);

    public abstract byte[] decryptData(final byte[] data);

    public abstract Message encodePayloadAsMessage(final String payload);

    public abstract String decodePayloadFromMessage(final Message message);

}
