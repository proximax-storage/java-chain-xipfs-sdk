package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.model.ProximaxChildMessage;

/**
 * The Class PlainPrivacyStrategy.
 */
public class PlainPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    /* (non-Javadoc)
     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#encrypt(byte[])
     */
    @Override
    public byte[] encrypt(final byte[] data) {
        return data;
    }

    /* (non-Javadoc)
     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#decrypt(byte[], org.nem.core.model.TransferTransaction, io.nem.xpx.service.model.buffers.ResourceHashMessage)
     */
    @Override
    public byte[] decrypt(final byte[] data, final TransferTransaction transaction, final ProximaxChildMessage message) {
        return data;
    }

}
