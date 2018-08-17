package io.proximax.exceptions;

/**
 * The exception when an upload parameter build failed
 */
public class UploadParameterBuildFailureException extends RuntimeException {

    /**
     * Create instance of this exception
     * @param message the exception message
     */
    public UploadParameterBuildFailureException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     * @param message the exception message
     * @param cause the cause of this exception
     */
    public UploadParameterBuildFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
