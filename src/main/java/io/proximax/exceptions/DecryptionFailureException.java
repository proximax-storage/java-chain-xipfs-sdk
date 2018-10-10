package io.proximax.exceptions;

/**
 * The exception when a privacy strategy decryption process has failed
 */
public class DecryptionFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DecryptionFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DecryptionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
