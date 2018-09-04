package io.proximax.exceptions;

/**
 * The exception when a direct download failed
 */
public class DirectDownloadFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DirectDownloadFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DirectDownloadFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
