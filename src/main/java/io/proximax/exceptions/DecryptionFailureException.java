package io.proximax.exceptions;


public class DecryptionFailureException extends RuntimeException {

	public DecryptionFailureException(String message) {
		super(message);
	}

	public DecryptionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
