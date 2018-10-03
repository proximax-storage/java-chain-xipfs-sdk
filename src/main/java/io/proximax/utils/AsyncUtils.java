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
package io.proximax.utils;

import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.reactivex.Observable;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The utility class for handling async calls
 */
public class AsyncUtils {

    private AsyncUtils() {
    }

    /**
     * Observe for the first item on the Observable then invoke callbacks from AsyncCallbacks using result.
     * Finally, update AsyncStatus to done.
     *
     * @param observable     the observable
     * @param asyncCallbacks the async callbacks
     * @param asyncTask      the async task that contains the state
     * @param <T>            the result type
     */
    public static <T> void processFirstItem(Observable<T> observable, AsyncCallbacks<T> asyncCallbacks, AsyncTask asyncTask) {
        checkParameter(observable != null, "observable is required");
        checkParameter(asyncTask != null, "asyncTask is required");

        observable.firstOrError()
                .subscribe(
                        result -> {
                            if (asyncCallbacks != null) {
                                asyncCallbacks.onSuccess(result);
                            }
                            asyncTask.done();
                        }, throwable -> {
                            if (asyncCallbacks != null) {
                                asyncCallbacks.onFailure(throwable);
                            }
                            asyncTask.done();
                        });
    }
}
