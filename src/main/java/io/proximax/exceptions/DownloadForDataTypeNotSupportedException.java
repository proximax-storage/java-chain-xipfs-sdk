package io.proximax.exceptions;

/**
 * The exception when downloading a not supported type
 */
public class DownloadForDataTypeNotSupportedException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DownloadForDataTypeNotSupportedException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DownloadForDataTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

}
