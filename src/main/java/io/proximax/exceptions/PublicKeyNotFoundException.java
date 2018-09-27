package io.proximax.exceptions;

/**
 * The exception when retrieving account public key of address but is not available yet on blockchain
 */
public class PublicKeyNotFoundException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public PublicKeyNotFoundException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public PublicKeyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
