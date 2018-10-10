package io.proximax.exceptions;

/**
 * The exception when detecting the content type
 */
public class DetectContenTypeFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DetectContenTypeFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DetectContenTypeFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
