package com.example.common.service.utils;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmptyOrNull(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean hasElements(Collection<?> collection) {
        return !isEmptyOrNull(collection);
    }
}
