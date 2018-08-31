package io.proximax.async;

import java.util.function.Consumer;

/**
 * This model class wraps the callbacks for asynchronous invocation
 * @param <T> the result type that this class will process
 */
public class AsyncCallback<T> {

    private final Consumer<T> successCallback;
    private final Consumer<Throwable> failureCallback;

    public AsyncCallback(Consumer<T> successCallback, Consumer<Throwable> failureCallback) {
        this.successCallback = successCallback;
        this.failureCallback = failureCallback;
    }

    /**
     * Create instance of this callback
     * @param successCallback the success callback
     * @param failureCallback the failure callback
     * @param <T> the result type
     * @return the async callback instance
     */
    public static <T> AsyncCallback<T> create(Consumer<T> successCallback, Consumer<Throwable> failureCallback) {
        return new AsyncCallback<T>(successCallback, failureCallback);
    }

    /**
     * Invoke success callback with result
     * @param result the result
     */
    public void onSuccess(T result) {
        if (successCallback != null) {
            successCallback.accept(result);
        }
    }

    /**
     * Invoke failure callback with exception
     * @param ex the exception
     */
    public void onFailure(Throwable ex) {
        if (failureCallback != null) {
            failureCallback.accept(ex);
        }
    }
}
