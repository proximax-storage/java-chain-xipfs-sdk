package io.proximax.steps;


/**
 * The Interface ReceiverOrSenderPublicKeyStep.
 *
 * @param <T> the generic type
 */
public interface ReceiverOrSenderPublicKeyStep<T> {
    
    /**
     * Receiver or sender public key.
     *
     * @param receiverOrSenderPublicKey the receiver or sender public key
     * @return the t
     */
    T receiverOrSenderPublicKey(String receiverOrSenderPublicKey);

}
