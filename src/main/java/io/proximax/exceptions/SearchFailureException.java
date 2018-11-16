package io.proximax.exceptions;

/**
 * The exception when an searching failed
 */
public class SearchFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public SearchFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public SearchFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
