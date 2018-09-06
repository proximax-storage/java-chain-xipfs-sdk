package io.proximax.exceptions;

/**
 * The exception when detecting the content type
 */
public class CipherInitFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public CipherInitFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public CipherInitFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
