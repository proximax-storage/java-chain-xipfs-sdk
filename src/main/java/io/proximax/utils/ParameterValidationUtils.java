package io.proximax.utils;

/**
 * The utility class to verify method parameters
 */
public class ParameterValidationUtils {

    private ParameterValidationUtils() {
    }

    /**
     * Validates a given parameter by accepting a resolved condition.
     * <br>
     * <br>
     * This throws an IllegalArgumentException with the provided message if not valid.
     * @param isValid a resolved validation result for a given parameter
     * @param invalidMessage the exception message to use when throwing exception
     */
    public static void checkParameter(boolean isValid, String invalidMessage) {
        if (!isValid) throw new IllegalArgumentException(invalidMessage);
    }

}
