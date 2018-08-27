package io.proximax.utils;

import java.util.Collection;

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
}
