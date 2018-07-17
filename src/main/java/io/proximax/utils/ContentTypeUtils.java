package io.proximax.utils;

import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;

import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;


public class ContentTypeUtils {

    private Tika tika;

    public ContentTypeUtils() {
        this.tika = new Tika();
    }

    public Observable<String> detectContentType(final byte[] data, final String contentType) {
        checkArgument(data != null, "data is required");

        return Observable.just(detectContentTypeFor(data, contentType));
    }

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
