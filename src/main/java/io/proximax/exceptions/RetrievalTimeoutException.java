package io.proximax.exceptions;

/**
 * The exception when an retrieving timed out
 */
public class RetrievalTimeoutException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public RetrievalTimeoutException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public RetrievalTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

}
