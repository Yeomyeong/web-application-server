package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by wymstar on 6/30/16.
 */
public class StringUtil {
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static String decode(String s) {
        try {
            return URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static String nvl(String s) {
        if (isEmpty(s))
            return "";
        return s;
    }

    public static Pair seperateBy(String source, String delimiter) {
        int splitIndex = source.indexOf(delimiter);
        if (splitIndex > 0) {
            String key = source.substring(0, splitIndex);
            String value = source.substring(splitIndex+1);
            return new Pair(key, value);
        }
        return null;
    }
}
