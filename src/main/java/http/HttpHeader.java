package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static util.StringUtil.isNotEmpty;

/**
 * Created by wymstar on 6/30/16.
 */
public class HttpHeader {
    private static final Logger log = LoggerFactory.getLogger(HttpHeader.class);

    private String requestURL;
    private String method;
    private String httpVersion;

    public Map<String, String> dataMap = new HashMap<>();

    public HttpHeader(String httpHeader) {
        parse(httpHeader);
    }

    private void parse(String httpHeader) {
        BufferedReader reader = new BufferedReader(new StringReader(httpHeader));

        try {
            String firstLine = reader.readLine();
            String[] tokens = firstLine.split(" ");

            this.method = tokens[0].trim();
            this.requestURL = tokens[1].trim();
            this.httpVersion = tokens[2].trim();

            String line;
            while (isNotEmpty(line = reader.readLine())) {
                int splitIndex = line.indexOf(":");
                if (splitIndex > 0) {
                    String key = line.substring(0, splitIndex);
                    String value = line.substring(splitIndex+1);
                    this.dataMap.put(key.trim(), value.trim());
                }
            }
        } catch ( IOException e) {
            log.error(this.toString() + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new IllegalHttpHeaderException();
        }
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getAttribute(String key) {
        return dataMap.get(key);
    }

    @Override
    public String toString() {
        return "METHOD : " + method + "\n"
                + "REQUEST URL : " + requestURL  + "\n"
                + "HTTP VERSION : " + httpVersion + "\n"
                + dataMap.toString();
    }
}
