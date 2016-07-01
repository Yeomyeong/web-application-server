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
}
