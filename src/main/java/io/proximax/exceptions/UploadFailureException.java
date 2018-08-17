package io.proximax.exceptions;

/**
 * The exception when an upload initialization failed
 */
public class UploadFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public UploadFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public UploadFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
