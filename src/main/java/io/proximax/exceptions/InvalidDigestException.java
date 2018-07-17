package io.proximax.exceptions;


public class InvalidDigestException extends RuntimeException {

	public InvalidDigestException(String message) {
		super(message);
	}

	public InvalidDigestException(String message, Throwable cause) {
		super(message, cause);
	}

}
