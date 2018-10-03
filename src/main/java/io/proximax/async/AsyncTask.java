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

/**
 * The model class to represent the async call invoked.
 * The class store the state of the async call
 */
public class AsyncTask {

    private boolean done;
    private boolean cancelled;

    /**
     * Construct instance
     */
    public AsyncTask() {
        done = false;
        cancelled = false;
    }

    /**
     * Check status if done
     *
     * @return the done status
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Check status if cancelled
     *
     * @return the cancelled status
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Set task to done
     */
    public void done() {
        if (!cancelled)
            this.done = true;
    }

    /**
     * Set task to cancel
     */
    public void cancel() {
        if (!done)
            this.cancelled = true;
    }
}
