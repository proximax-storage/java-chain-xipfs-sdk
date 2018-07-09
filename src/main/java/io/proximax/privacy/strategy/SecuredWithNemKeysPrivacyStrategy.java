//package io.proximax.privacy.strategy;
//
//import io.nem.core.crypto.CryptoEngines;
//import io.nem.sdk.model.transaction.TransferTransaction;
//import io.proximax.model.ProximaxChildMessage;
//
///**
// * The Class SecuredWithNemKeysPrivacyStrategy.
// */
//public final class SecuredWithNemKeysPrivacyStrategy extends AbstractSecureMessagePrivacyStrategy {
//
//
//    /**
//     * Instantiates a new secured with nem keys privacy strategy.
//     *
//     * @param privateKey the private key
//     * @param publicKey the public key
//     */
//    public SecuredWithNemKeysPrivacyStrategy(String privateKey, String publicKey) {
//        super(privateKey, publicKey);
//    }
//
//    /* (non-Javadoc)
//     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#encrypt(byte[])
//     */
//    @Override
//    public byte[] encrypt(final byte[] data) {
//        return CryptoEngines.defaultEngine().createBlockCipher(keyPairOfPrivateKey, keyPairOfPublicKey).encrypt(data);
//    }
//
//    /* (non-Javadoc)
//     * @see io.nem.xpx.strategy.privacy.PrivacyStrategy#decrypt(byte[], org.nem.core.model.TransferTransaction, io.nem.xpx.service.model.buffers.ResourceHashMessage)
//     */
//    @Override
//    public byte[] decrypt(final byte[] data, final TransferTransaction transaction, final ProximaxChildMessage message) {
//
//        if (transaction != null && !transaction.getSigner().getAddress().getEncoded().equals(accountWithPrivateKey.getAddress().getEncoded()) &&
//                !transaction.getRecipient().getAddress().getEncoded().equals(accountWithPrivateKey.getAddress().getEncoded())) {
//            throw new DecryptionFailureException("Decrypt of data is unsuccessful");
//        }
//        return CryptoEngines.defaultEngine().createBlockCipher(keyPairOfPublicKey, keyPairOfPrivateKey).decrypt(data);
//
//    }
//}
