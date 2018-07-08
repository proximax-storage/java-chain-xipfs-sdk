package io.proximax.steps;


/**
 * The Interface EncodingStep.
 *
 * @param <T> the generic type
 */
public interface EncodingStep<T> {
    
    /**
     * Encoding.
     *
     * @param encoding the encoding
     * @return the t
     */
    T encoding(String encoding);
}
