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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;

public class AsyncUtilsTest {

    @Mock
    private AsyncCallbacks<String> mockAsyncCallbacks;

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
    public void shouldSetAsyncTaskToDoneOnSuccess() throws InterruptedException {
        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(Observable.just("abc"), null, asyncTask);
        Thread.sleep(100);

        assertThat(asyncTask.isDone(), is(true));
    }

    @Test
    public void shouldSetAsyncTaskToDoneOnFailure() throws InterruptedException {
        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(Observable.error(new Exception()), null, asyncTask);
        Thread.sleep(100);

        assertThat(asyncTask.isDone(), is(true));
    }

    @Test
    public void shouldCallSuccessCallback() throws InterruptedException {
        AsyncUtils.processFirstItem(Observable.just("abc"), mockAsyncCallbacks, new AsyncTask());
        Thread.sleep(100);

        verify(mockAsyncCallbacks).onSuccess("abc");
    }

    @Test
    public void shouldCallFailureCallback() throws InterruptedException {
        final Exception sampleException = new Exception();

        AsyncUtils.processFirstItem(Observable.error(sampleException), mockAsyncCallbacks, new AsyncTask());
        Thread.sleep(100);

        verify(mockAsyncCallbacks).onFailure(sampleException);
    }
}
