/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.proximax.async;

import java.util.function.Consumer;

/**
 * This model class wraps the callbacks for asynchronous invocation
 *
 * @param <T> the result type that this class will process
 */
public class AsyncCallbacks<T> {

    private final Consumer<T> successCallback;
    private final Consumer<Throwable> failureCallback;

    public AsyncCallbacks(Consumer<T> successCallback, Consumer<Throwable> failureCallback) {
        this.successCallback = successCallback;
        this.failureCallback = failureCallback;
    }

    /**
     * Create instance of this callback
     *
     * @param successCallback the success callback
     * @param failureCallback the failure callback
     * @param <T>             the result type
     * @return the async callback instance
     */
    public static <T> AsyncCallbacks<T> create(Consumer<T> successCallback, Consumer<Throwable> failureCallback) {
        return new AsyncCallbacks<T>(successCallback, failureCallback);
    }

    /**
     * Invoke success callback with result
     *
     * @param result the result
     */
    public void onSuccess(T result) {
        if (successCallback != null) {
            successCallback.accept(result);
        }
    }

    /**
     * Invoke failure callback with exception
     *
     * @param ex the exception
     */
    public void onFailure(Throwable ex) {
        if (failureCallback != null) {
            failureCallback.accept(ex);
        }
    }
}
