package io.proximax.exceptions;


public class GetTransactionFailureException extends RuntimeException {

	public GetTransactionFailureException(String message) {
		super(message);
	}

	public GetTransactionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
