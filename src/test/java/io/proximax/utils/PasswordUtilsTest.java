package io.proximax.utils;

import org.junit.Test;

import static io.proximax.privacy.strategy.PasswordPrivacyStrategy.MINIMUM_PASSWORD_LENGTH;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PasswordUtilsTest {

    @Test
    public void shouldGenerateCharPasswordWithDefaultLength() {
        final String generatedPassword = PasswordUtils.generatePassword();

        assertThat(generatedPassword, is(notNullValue()));
        assertThat(generatedPassword.length(), is(MINIMUM_PASSWORD_LENGTH));
    }

    @Test
    public void shouldGeneratePasswordWithSpecialCharacters() {
        final String generatedPassword = PasswordUtils.generatePassword(true);

        assertThat(generatedPassword, is(notNullValue()));
        assertThat(generatedPassword.length(), is(MINIMUM_PASSWORD_LENGTH));
    }

    @Test
    public void shouldGeneratePasswordWithCustomLength() {
        assertThat(PasswordUtils.generatePassword(MINIMUM_PASSWORD_LENGTH * 2).length(), is(MINIMUM_PASSWORD_LENGTH * 2));
        assertThat(PasswordUtils.generatePassword(MINIMUM_PASSWORD_LENGTH + 10).length(), is(MINIMUM_PASSWORD_LENGTH + 10));
        assertThat(PasswordUtils.generatePassword(MINIMUM_PASSWORD_LENGTH).length(), is(MINIMUM_PASSWORD_LENGTH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenGeneratingPasswordBelowMinimumLength() {
        PasswordUtils.generatePassword(MINIMUM_PASSWORD_LENGTH - 1);
    }

}
