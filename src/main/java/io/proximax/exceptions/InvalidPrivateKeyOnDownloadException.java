package io.proximax.exceptions;

/**
 * The exception when downloading a transaction with secure message using a wrong private key
 */
public class InvalidPrivateKeyOnDownloadException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public InvalidPrivateKeyOnDownloadException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public InvalidPrivateKeyOnDownloadException(String message, Throwable cause) {
		super(message, cause);
	}

}
