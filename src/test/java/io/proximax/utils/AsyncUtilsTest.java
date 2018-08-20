package io.proximax.utils;

import io.proximax.async.AsyncCallback;
import io.proximax.async.AsyncTask;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;

public class AsyncUtilsTest {

    @Mock
    private AsyncCallback<String> mockAsyncCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullObservable() {
        AsyncUtils.processFirstItem(null, null, new AsyncTask());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullAsyncTask() {
        AsyncUtils.processFirstItem(Observable.just("abc"), null, null);
    }

    @Test
    public void shouldSetAsyncTaskToDone() throws InterruptedException {
        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(Observable.just("abc"), null, asyncTask);
        Thread.sleep(100);

        assertThat(asyncTask.isDone(), is(true));
    }

    @Test
    public void shouldCallSuccessCallback() throws InterruptedException {
        AsyncUtils.processFirstItem(Observable.just("abc"), mockAsyncCallback, new AsyncTask());
        Thread.sleep(100);

        verify(mockAsyncCallback).onSuccess("abc");
    }

    @Test
    public void shouldCallFailureCallback() throws InterruptedException {
        final Exception sampleException = new Exception();

        AsyncUtils.processFirstItem(Observable.error(sampleException), mockAsyncCallback, new AsyncTask());
        Thread.sleep(100);

        verify(mockAsyncCallback).onFailure(sampleException);
    }
}
