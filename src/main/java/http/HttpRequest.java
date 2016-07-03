package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static util.StringUtil.isNotEmpty;

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

    public Map<String, String> headerData = new HashMap<>();
    public Map<String, String> parameters = new HashMap<>();

    public HttpRequest(InputStream connectionIn) {
        parse(connectionIn);
    }

    public HttpRequest(String headerText) {
        parse( headerText);
    }

    private void parse(InputStream connectionIn) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connectionIn));

        parse(reader);
    }

    private void parse(String headerText) {
        BufferedReader reader = new BufferedReader(new StringReader(headerText));

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

            if ("POST".equals(method)) {
                int contentLength = Integer.parseInt(headerData.get("Content-Length"));
                this.data = IOUtils.readData(reader, contentLength);
                this.path = requestURL;
            } else if ("GET".equals(method)) {
                int splitIndex = requestURL.indexOf("?");
                this.path = requestURL;
                if (splitIndex > 0) {
                    this.path = requestURL.substring(0, splitIndex);
                    this.data = requestURL.substring(splitIndex + 1);
                }
            }
            this.putIntoParameter();

        } catch ( IOException e) {
            log.error(this.toString() + e.getMessage(), e);
        } catch (RuntimeException e) {
            log.error(this.toString() + e.getMessage(), e);
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

    public String getHeaderData(String key) {
        return headerData.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
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
