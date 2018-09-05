package io.proximax.utils;

import io.reactivex.Observable;
import org.apache.tika.Tika;

import static com.google.common.base.Preconditions.checkArgument;

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
     * Detect the content type for the given data
     * @param data the data represented as byte array
     * @return the detected content type
     */
    public Observable<String> detectContentType(final byte[] data) {
        checkArgument(data != null, "data is required");

        return Observable.just(tika.detect(data));
    }
}
