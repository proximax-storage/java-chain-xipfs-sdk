package io.proximax.exceptions;

/**
 * The exception when retrieving account of address but is not available yet on blockchain
 */
public class AccountNotFoundException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public AccountNotFoundException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public AccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
