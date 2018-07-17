package io.proximax.exceptions;


public class TransactionNotAllowedException extends RuntimeException {

	public TransactionNotAllowedException(String message) {
		super(message);
	}

	public TransactionNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

}
