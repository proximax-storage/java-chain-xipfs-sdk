package io.proximax.exceptions;

/**
 * The exception when an upload parameter data is not supported
 */
public class UploadParameterDataNotSupportedException extends RuntimeException {

    /**
     * Create instance of this exception
     * @param message the exception message
     */
    public UploadParameterDataNotSupportedException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     * @param message the exception message
     * @param cause the cause of this exception
     */
    public UploadParameterDataNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
