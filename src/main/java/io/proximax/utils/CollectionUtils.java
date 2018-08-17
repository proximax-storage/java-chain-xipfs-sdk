package io.proximax.utils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * The utility class for inspecting collection
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * Check if a collection is null or empty
     * @param collection the collection to inspect
     * @return a flag that indicates if null or empty
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Check if a collection is not null and empty
     * @param collection the collection to inspect
     * @return a flag that indicates if not null and empty
     */
    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * Count the number of non-null objects on the collection
     * @param collection the collection to inspect
     * @return the number of non-null objects on the collection
     */
    public static long nonNullCount(Collection collection) {
        return collection.stream().filter(Objects::nonNull).count();
    }

    /**
     * Compare if two lists are equal in terms of their contents
     * @param list1 the first list to compare
     * @param list2 the second list to compare
     * @return the flag that indicates if lists are equal
     */
    public static boolean isEqualList(List list1, List list2) {
        return list1.size() == list2.size() &&
                IntStream.range(0, list1.size())
                        .mapToObj(index -> list1.get(index).equals(list2.get(index)))
                        .allMatch(Boolean::booleanValue);
    }
}
