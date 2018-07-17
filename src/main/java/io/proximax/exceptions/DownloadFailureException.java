package io.proximax.exceptions;


public class DownloadFailureException extends RuntimeException {

	public DownloadFailureException(String message) {
		super(message);
	}

	public DownloadFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
