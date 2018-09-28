package io.proximax.exceptions;

/**
 * The exception when downloading a transaction with secure message with no private key
 */
public class MissingPrivateKeyOnDownloadException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public MissingPrivateKeyOnDownloadException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public MissingPrivateKeyOnDownloadException(String message, Throwable cause) {
		super(message, cause);
	}

}
