package io.proximax.utils;

import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The utility class for detecting content types when unknown
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
     * Detect the content type for the given data if unknown
     * @param data the data represented as byte array
     * @param contentType the specified content type for the data
     * @return the specified content type or the detected content type
     */
    public Observable<String> detectContentType(final byte[] data, final String contentType) {
        checkArgument(data != null, "data is required");

        return Observable.just(StringUtils.isEmpty(contentType) ? tika.detect(data) : contentType);
    }
}
