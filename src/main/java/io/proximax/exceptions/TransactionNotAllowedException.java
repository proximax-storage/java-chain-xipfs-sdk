package io.proximax.exceptions;

/**
 * The exception when the transaction type is not allowed for the action done
 */
public class TransactionNotAllowedException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public TransactionNotAllowedException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public TransactionNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

}
