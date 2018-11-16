package io.proximax.utils;

import io.proximax.exceptions.RetrievalTimeoutException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * The utility class for handling timeouts
 */
public class TimeoutUtils {

    private TimeoutUtils() {}

    /**
     * Timeout retrieval
     * @param supplier the supplier
     * @param timeout timeout length
     * @param timeUnit time unit of timeout
     * @param <T> the class the supplier provide
     * @return the retrieved type
     */
    public static <T> T get(Supplier<T> supplier, long timeout, TimeUnit timeUnit) {
        try {
            return CompletableFuture.supplyAsync(supplier).get(timeout, timeUnit);
        } catch (TimeoutException e) {
            throw new RetrievalTimeoutException("Retrieval timed out", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
