package io.proximax.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class ParameterValidationUtilsTest {

    @Test
    public void shouldThrowExceptionWhenNotValidOnCheckParameter() {
        try {
            ParameterValidationUtils.checkParameter(false, "INVALID");
            fail("should have thrown exception");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("INVALID"));
        }
    }

    @Test
    public void shouldDoNothingOnCheckParameterWhenValid() {
        ParameterValidationUtils.checkParameter(true, "INVALID");
    }

    @Test
    public void shouldThrowExceptionWhenNotValidOnCheckParameterSupplier() {
        try {
            ParameterValidationUtils.checkParameter(() -> false, "INVALID");
            fail("should have thrown exception");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("INVALID"));
        }
    }

    @Test
    public void shouldThrowExceptionWhenErrorOnCheckParameterSupplier() {
        try {
            ParameterValidationUtils.checkParameter(
                    () -> {
                        if (false)
                            return true;
                        else
                            throw new IllegalArgumentException("not valid");
                    }, "INVALID");
            fail("should have thrown exception");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("INVALID"));
        }
    }

    @Test
    public void shouldDoNothingOnCheckParameterSupplierWhenValid() {
        ParameterValidationUtils.checkParameter(() -> true, "INVALID");
    }
}
