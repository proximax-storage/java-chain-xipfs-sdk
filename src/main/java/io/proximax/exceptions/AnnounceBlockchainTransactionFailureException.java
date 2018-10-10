package io.proximax.exceptions;

/**
 * The exception when the announce transaction has failed
 */
public class AnnounceBlockchainTransactionFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public AnnounceBlockchainTransactionFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public AnnounceBlockchainTransactionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
