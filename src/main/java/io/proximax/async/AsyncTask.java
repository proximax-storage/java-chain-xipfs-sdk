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
     * @return the done status
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Check status if cancelled
     * @return the cancelled status
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Set task to done
     */
    public void done() {
        if (cancelled == false)
            this.done = true;
    }

    /**
     * Set task to cancel
     */
    public void cancel() {
        if (done == false)
            this.cancelled = true;
    }
}
