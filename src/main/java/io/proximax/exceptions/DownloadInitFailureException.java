package io.proximax.exceptions;

/**
 * The exception when a download initialization failed
 */
public class DownloadInitFailureException extends RuntimeException {

    /**
     * Create instance of this exception
     * @param message the exception message
     */
    public DownloadInitFailureException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     * @param message the exception message
     * @param cause the cause of this exception
     */
    public DownloadInitFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
