package io.proximax.exceptions;

/**
 * The exception when a privacy strategy encryption process has failed
 */
public class EncryptionFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public EncryptionFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public EncryptionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
