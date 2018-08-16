package io.proximax.utils;

import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;

import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

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

        return Observable.just(detectContentTypeFor(data, contentType));
    }

    /**
     * Detect the content type for the given list data if unknown
     * @param dataList the list of data
     * @param contentTypeList the specified content types for the list of data
     * @return the list of content types from specified or detected
     */
    public Observable<List<String>> detectContentTypeForList(final List<byte[]> dataList, final List<String> contentTypeList) {
        checkArgument(dataList != null, "dataList is required");
        checkArgument(contentTypeList != null, "contentTypeList is required");

        return Observable.just(IntStream.range(0, dataList.size())
                .mapToObj(index -> detectContentTypeFor(dataList.get(index), contentTypeList.get(index)))
                .collect(toList()));
    }

    private String detectContentTypeFor(final byte[] data, final String contentType) {
        return StringUtils.isEmpty(contentType) ? tika.detect(data) : contentType;
    }
}
