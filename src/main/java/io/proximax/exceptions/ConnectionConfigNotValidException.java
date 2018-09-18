package io.proximax.exceptions;


/**
 * The exception when a connection contains some invalid config
 */
public class ConnectionConfigNotValidException extends RuntimeException {

    /**
     * Create instance of this exception
     * @param message the exception message
     */
    public ConnectionConfigNotValidException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     * @param message the exception message
     * @param cause the cause of this exception
     */
    public ConnectionConfigNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

}
