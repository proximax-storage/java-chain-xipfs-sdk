package io.proximax.exceptions;

/**
 * The exception when retrieving transaction failed
 */
public class GetTransactionFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public GetTransactionFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public GetTransactionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
