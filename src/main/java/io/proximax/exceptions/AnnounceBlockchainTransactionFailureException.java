package io.proximax.exceptions;


public class AnnounceBlockchainTransactionFailureException extends RuntimeException {

	public AnnounceBlockchainTransactionFailureException(String message) {
		super(message);
	}

	public AnnounceBlockchainTransactionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
