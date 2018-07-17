package io.proximax.exceptions;


public class IpfsClientFailureException extends RuntimeException {

	public IpfsClientFailureException(String message) {
		super(message);
	}

	public IpfsClientFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
