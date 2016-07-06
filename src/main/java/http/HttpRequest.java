package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import util.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static util.StringUtil.isNotEmpty;
import static util.StringUtil.nvl;
import static util.StringUtil.seperateBy;

/**
 * Created by wymstar on 6/30/16.
 */
public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String requestURL = "";
    private String method = "";
    private String httpVersion = "";
    private String path = "";
    private String data = "";

    private Map<String, String> cookies = new HashMap<>();
    private Map<String, String> headerData = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();

    public HttpRequest(InputStream connectionIn) {
        parse(connectionIn);
    }

    private void parse(InputStream connectionIn) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connectionIn));

        parse(reader);
    }

    private void parse(BufferedReader reader) {
        try {
            String firstLine = reader.readLine();
            String[] tokens = firstLine.split(" ");
            log.debug(firstLine + "\n");

            this.method = tokens[0].trim();
            this.requestURL = tokens[1].trim();
            this.httpVersion = tokens[2].trim();

            String line;
            while (isNotEmpty(line = reader.readLine()) ) {
                log.debug(line + "\n");
                int splitIndex = line.indexOf(":");
                if (splitIndex > 0) {
                    String key = line.substring(0, splitIndex);
                    String value = line.substring(splitIndex+1);
                    this.headerData.put(key.trim(), value.trim());
                }
            }

            String host = headerData.get("Host");
            String path = requestURL.replaceAll("http[s]?://", "").replaceAll(host, "");
            this.path = path;
            if ("POST".equals(method)) {
                int contentLength = Integer.parseInt(headerData.get("Content-Length"));
                this.data = IOUtils.readData(reader, contentLength);
            } else if ("GET".equals(method)) {
                int splitIndex = requestURL.indexOf("?");
                if (splitIndex > 0) {
                    this.path = path.substring(0, splitIndex);
                    this.data = path.substring(splitIndex + 1);
                }
            }
            this.putIntoParameter();
            this.setCookies();

        } catch ( IOException e) {
            log.error(this.toString() + e.getMessage(), e);
        } catch (RuntimeException e) {
            log.error(this.toString() + e.getMessage(), e);
            throw new IllegalHttpHeaderException();
        }
    }

    private void setCookies() {
        String rawCookies = nvl(headerData.get("Cookie"));
        for (String rawCookie : rawCookies.split(";")) {
            Pair pair = seperateBy(rawCookie, "=");
            if (pair != null)
                cookies.put(pair.getKey(), pair.getValue());
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

    public String getHeaderData(String key) {
        return headerData.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public String getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    private void putIntoParameter() {
        String[] parameterArray = this.data.split("&");
        for (String keyValueArr : parameterArray) {
            String[] keyValue = keyValueArr.split("=");
            if (keyValue.length == 2) {
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    @Override
    public String toString() {
        return "METHOD : " + method + "\n"
                + "REQUEST URL : " + requestURL  + "\n"
                + "HTTP VERSION : " + httpVersion + "\n"
                + headerData.toString();
    }
}
