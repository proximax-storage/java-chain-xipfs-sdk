package io.proximax.utils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class CollectionUtils {

    private CollectionUtils() {

    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static long nonNullCount(Collection collection) {
        return collection.stream().filter(Objects::nonNull).count();
    }

    public static boolean isEqualList(List list1, List list2) {
        return list1.size() == list2.size() &&
                IntStream.range(0, list1.size())
                        .mapToObj(index -> list1.get(index).equals(list2.get(index)))
                        .allMatch(Boolean::booleanValue);
    }
}
