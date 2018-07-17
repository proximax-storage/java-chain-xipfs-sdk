package io.proximax.exceptions;

public class UploadParameterBuildFailureException extends RuntimeException {

    public UploadParameterBuildFailureException(String message) {
        super(message);
    }

    public UploadParameterBuildFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
