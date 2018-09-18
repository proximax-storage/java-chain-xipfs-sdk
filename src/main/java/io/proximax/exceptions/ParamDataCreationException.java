package io.proximax.exceptions;

/**
 * The exception when an upload parameter creation failed
 */
public class ParamDataCreationException extends RuntimeException {

    /**
     * Create instance of this exception
     * @param message the exception message
     */
    public ParamDataCreationException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     * @param message the exception message
     * @param cause the cause of this exception
     */
    public ParamDataCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
