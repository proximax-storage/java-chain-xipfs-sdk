package io.proximax.utils;

import io.proximax.async.AsyncCallback;
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
     * Observe for the first item on the Observable then invoke callbacks from AsyncCallback using result.
     * Finally, update AsyncStatus to done.
     * @param observable the observable
     * @param asyncCallback the async callbacks
     * @param asyncTask the async task that contains the state
     * @param <T> the result type
     */
    public static <T> void processFirstItem(Observable<T> observable, AsyncCallback<T> asyncCallback, AsyncTask asyncTask) {
        checkParameter(observable != null, "observable is required");
        checkParameter(asyncTask != null, "asyncTask is required");

        observable.firstOrError()
                .subscribe(
                        result -> {
                            if (asyncCallback != null) {
                                asyncCallback.onSuccess(result);
                            }
                            asyncTask.done();
                        }, throwable -> {
                            if (asyncCallback != null) {
                                asyncCallback.onFailure(throwable);
                            }
                            asyncTask.done();
                        });
    }
}
