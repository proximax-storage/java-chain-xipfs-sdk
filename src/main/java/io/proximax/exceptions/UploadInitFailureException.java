package io.proximax.exceptions;

public class UploadInitFailureException extends RuntimeException {

    public UploadInitFailureException(String message) {
        super(message);
    }

    public UploadInitFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
