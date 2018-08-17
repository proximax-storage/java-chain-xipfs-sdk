package io.proximax.exceptions;

/**
 * The exception when digest validation failed
 */
public class DigestDoesNotMatchException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DigestDoesNotMatchException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DigestDoesNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

}
