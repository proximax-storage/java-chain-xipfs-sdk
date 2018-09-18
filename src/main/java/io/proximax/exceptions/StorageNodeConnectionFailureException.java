package io.proximax.exceptions;

/**
 * The exception when connecting to a storage connection
 */
public class StorageNodeConnectionFailureException extends RuntimeException {

    /**
     * Create instance of this exception
     *
     * @param message the exception message
     */
    public StorageNodeConnectionFailureException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     *
     * @param message the exception message
     * @param cause   the cause of this exception
     */
    public StorageNodeConnectionFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
