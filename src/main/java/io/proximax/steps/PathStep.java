package io.proximax.steps;


/**
 * The Interface PathStep.
 *
 * @param <T> the generic type
 */
public interface PathStep<T> {
    
    /**
     * Path.
     *
     * @param path the path
     * @return the t
     */
    T path(String path);
}
