package io.proximax.utils;

import io.proximax.exceptions.RetrievalTimeoutException;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TimeoutUtilsTest {

    public static final Supplier<String> SUPPLIER = () -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return "Hello World";
    };

    @Test
    public void shouldRetrieveWithinTimeout() {
        final String result = TimeoutUtils.get(SUPPLIER, 200, TimeUnit.MILLISECONDS);

        assertThat(result, is("Hello World"));
    }

    @Test(expected = RetrievalTimeoutException.class)
    public void failRetrieveOnTimeout() {
        TimeoutUtils.get(SUPPLIER, 50, TimeUnit.MILLISECONDS);
    }
}
