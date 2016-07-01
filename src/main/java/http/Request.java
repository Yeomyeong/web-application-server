package http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wymstar on 6/30/16.
 */
public class Request {
    private String requestURI;
    private String path;
    private Map<String,String> parameter = new HashMap<>();

    public Request(String headerRequestURI) {
        this.requestURI = headerRequestURI;
        
        int splitIndex = headerRequestURI.indexOf("?");
        if (splitIndex > 0) {
            this.path = headerRequestURI.substring(0, splitIndex);
            this.putIntoParameter( headerRequestURI.substring(splitIndex + 1) );
        } else {
            this.path = requestURI;
        }
    }

    private void putIntoParameter(String queryString) {
        String[] parameterArray = queryString.split("&");
        for (String keyValueArr : parameterArray) {
            String[] keyValue = keyValueArr.split("=");
            if (keyValue.length == 2) {
                parameter.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }
}
