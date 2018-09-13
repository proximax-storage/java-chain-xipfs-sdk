package io.proximax.utils;

import io.proximax.exceptions.DetectContenTypeFailureException;
import io.reactivex.Observable;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The utility class for detecting content types
 */
public class ContentTypeUtils {

    private Tika tika;

    /**
     * Construct an instance of this utility class
     */
    public ContentTypeUtils() {
        this.tika = new Tika();
    }

    /**
     * Detect the content type for the byte stream
     * @param byteStream the byte stream
     * @return the detected content type
     */
    public Observable<String> detectContentType(final InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        try {
            return Observable.just(tika.detect(byteStream));
        } catch (IOException e) {
            throw new DetectContenTypeFailureException("Failed to detect content type", e);
        }
    }
}
