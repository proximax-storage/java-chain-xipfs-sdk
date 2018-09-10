package io.proximax.utils;

import java.security.SecureRandom;

import static io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy.MINIMUM_PASSWORD_LENGTH;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class PasswordUtils {

    private static final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SPECIAL_CHARACTERS = "~!@#$%^&*()-_=+[]{};:'\",./<>?";

    public static String generatePassword() {
        return generatePassword(MINIMUM_PASSWORD_LENGTH);
    }

    public static String generatePassword(int length) {
        return generatePassword(length, false);
    }

    public static String generatePassword(boolean allowSpecialCharacters) {
        return generatePassword(MINIMUM_PASSWORD_LENGTH, allowSpecialCharacters);
    }

    public static String generatePassword(int length, boolean allowSpecialCharacters) {
        checkParameter(length >= MINIMUM_PASSWORD_LENGTH,
                String.format("Password length should be longer than %d. %d was provided", MINIMUM_PASSWORD_LENGTH, length));

        final SecureRandom random = new SecureRandom();

        final String allowedCharacters = allowSpecialCharacters ? ALPHA_NUMERIC + SPECIAL_CHARACTERS : ALPHA_NUMERIC;

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedCharacters.length());
            stringBuilder.append(allowedCharacters.charAt(index));
        }
        return stringBuilder.toString();
    }
}
