package io.proximax.exceptions;

/**
 * The exception when an upload initialization failed
 */
public class UploadInitFailureException extends RuntimeException {

    /**
     * Create instance of this exception
     * @param message the exception message
     */
    public UploadInitFailureException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     * @param message the exception message
     * @param cause the cause of this exception
     */
    public UploadInitFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
