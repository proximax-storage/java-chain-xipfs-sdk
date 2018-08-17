package io.proximax.exceptions;

/**
 * The exception when a download failed
 */
public class DownloadFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DownloadFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DownloadFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
