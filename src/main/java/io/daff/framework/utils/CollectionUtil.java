package io.daff.framework.utils;

import java.util.Collection;

/**
 * @author daff
 * @since 2021/8/21
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
