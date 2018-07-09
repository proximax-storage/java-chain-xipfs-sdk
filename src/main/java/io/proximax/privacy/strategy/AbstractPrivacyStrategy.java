package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.model.ProximaxChildMessage;

/**
 * The Class PrivacyStrategy.
 */
public abstract class AbstractPrivacyStrategy {

    /**
     * Encrypt.
     *
     * @param data the data
     * @return the byte[]
     */
    public abstract byte[] encrypt(final byte[] data);
    
    /**
     * Decrypt.
     *
     * @param data the data
     * @param transaction the transaction
     * @param hashMessage the hash message
     * @return the byte[]
     */
    public abstract byte[] decrypt(final byte[] data, final TransferTransaction transaction, final ProximaxChildMessage message);

    /**
     * Encode to message.
     *
     * @param payload the payload
     * @return the message
     */
    public abstract Message encodeToMessage(final byte[] payload);
    
    /**
     * Decode transaction.
     *
     * @param transaction the transaction
     * @return the byte[]
     */
    public abstract byte[] decodeTransaction(final TransferTransaction transaction);

}
