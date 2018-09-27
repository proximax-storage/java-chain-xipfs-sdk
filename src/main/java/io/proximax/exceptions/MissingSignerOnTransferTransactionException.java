package io.proximax.exceptions;

/**
 * The exception when a signer is not available on transaction
 */
public class MissingSignerOnTransferTransactionException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public MissingSignerOnTransferTransactionException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public MissingSignerOnTransferTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

}
