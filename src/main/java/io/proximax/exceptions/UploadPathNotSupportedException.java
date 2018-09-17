package io.proximax.exceptions;

/**
 * The exception when uploading path using a storage connection
 */
public class UploadPathNotSupportedException extends RuntimeException {

    /**
     * Create instance of this exception
     *
     * @param message the exception message
     */
    public UploadPathNotSupportedException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     *
     * @param message the exception message
     * @param cause   the cause of this exception
     */
    public UploadPathNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

}
