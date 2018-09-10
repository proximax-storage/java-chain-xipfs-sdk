package io.proximax.exceptions;

/**
 * The exception when open the byte stream to upload
 */
public class GetByteStreamFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public GetByteStreamFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public GetByteStreamFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
