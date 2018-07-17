package io.proximax.exceptions;


public class UploadFailureException extends RuntimeException {

	public UploadFailureException(String message) {
		super(message);
	}

	public UploadFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
