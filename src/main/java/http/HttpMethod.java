package http;

/**
 * Created by aymonwoo on 2016. 7. 7..
 */
public enum HttpMethod {
    GET, POST;

    public static HttpMethod getMethod(String method ) {
        for (HttpMethod result : HttpMethod.values()) {
            if (method.equalsIgnoreCase(result.name())) {
                return result;
            }
        }
        return GET;
    }
}
