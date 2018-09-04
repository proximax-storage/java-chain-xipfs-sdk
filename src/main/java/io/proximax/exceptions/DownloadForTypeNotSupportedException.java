package io.proximax.exceptions;

/**
 * The exception when downloading a not supported type
 */
public class DownloadForTypeNotSupportedException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DownloadForTypeNotSupportedException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DownloadForTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

}
