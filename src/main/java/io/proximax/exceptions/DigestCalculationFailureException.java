package io.proximax.exceptions;

/**
 * The exception when digest calculation failed
 */
public class DigestCalculationFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public DigestCalculationFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public DigestCalculationFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
