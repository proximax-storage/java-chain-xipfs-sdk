package io.proximax.exceptions;

/**
 * The exception when an IPFS action failed
 */
public class IpfsClientFailureException extends RuntimeException {

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 */
	public IpfsClientFailureException(String message) {
		super(message);
	}

	/**
	 * Create instance of this exception
	 * @param message the exception message
	 * @param cause the cause of this exception
	 */
	public IpfsClientFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
