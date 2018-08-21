package io.proximax.exceptions;

/**
 * The exception when a download data failed
 */
public class DownloadDataFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DownloadDataFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DownloadDataFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
