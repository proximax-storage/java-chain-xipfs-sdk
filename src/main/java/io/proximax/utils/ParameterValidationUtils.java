package io.proximax.utils;

/**
 * Utility class to verify method parameters
 */
public class ParameterValidationUtils {

    private ParameterValidationUtils() {
    }

    /**
     * When a given parameter is invalid, this throws an IllegalArgumentException with the provided message.
     * @param isValid a resolved validation result for a given parameter
     * @param invalidMessage the exception message to use when throwing exception
     */
    public static void checkParameter(boolean isValid, String invalidMessage) {
        if (!isValid) throw new IllegalArgumentException(invalidMessage);
    }

}
