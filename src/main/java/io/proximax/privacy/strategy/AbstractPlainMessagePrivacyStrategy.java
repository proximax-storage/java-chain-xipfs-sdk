package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.PlainMessage;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.exceptions.DecodeNemMessageFailureException;

/**
 * The Class AbstractPlainMessagePrivacyStrategy.
 */
public abstract class AbstractPlainMessagePrivacyStrategy extends AbstractPrivacyStrategy {


    /* (non-Javadoc)
     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#encodeToMessage(byte[])
     */
    @Override
    public Message encodeToMessage(byte[] data) {
        return new PlainMessage(new String(data));
    }

    /* (non-Javadoc)
     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#decodeTransaction(org.nem.core.model.TransferTransaction)
     */
    @Override
    public byte[] decodeTransaction(TransferTransaction transaction) {
        if (transaction.getMessage().getType() != 0)
            throw new DecodeNemMessageFailureException("Unable to decode secure message with plain privacy strategy");
        return transaction.getMessage().getPayload().getBytes();
    }
}
