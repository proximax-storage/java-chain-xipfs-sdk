package io.proximax.exceptions;

/**
 * The exception when downloading a not supported type
 */
public class DownloadForMessageTypeNotSupportedException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DownloadForMessageTypeNotSupportedException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DownloadForMessageTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

}
