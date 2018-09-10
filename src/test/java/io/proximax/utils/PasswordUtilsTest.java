package io.proximax.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PasswordUtilsTest {

    @Test
    public void shouldGenerateCharPasswordWithDefaultLength() {
        final String generatedPassword = PasswordUtils.generatePassword();

        assertThat(generatedPassword, is(notNullValue()));
        assertThat(generatedPassword.length(), is(50));
    }

    @Test
    public void shouldGeneratePasswordWithSpecialCharacters() {
        final String generatedPassword = PasswordUtils.generatePassword(true);

        assertThat(generatedPassword, is(notNullValue()));
        assertThat(generatedPassword.length(), is(50));
    }

    @Test
    public void shouldGeneratePasswordWithCustomLength() {
        assertThat(PasswordUtils.generatePassword(100).length(), is(100));
        assertThat(PasswordUtils.generatePassword(60).length(), is(60));
        assertThat(PasswordUtils.generatePassword(55).length(), is(55));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenGeneratingPasswordBelowMinimumLength() {
        PasswordUtils.generatePassword(10);
    }

}
