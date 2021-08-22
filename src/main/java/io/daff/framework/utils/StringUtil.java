package io.daff.framework.utils;

/**
 * @author daff
 * @since 2021/8/22
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
