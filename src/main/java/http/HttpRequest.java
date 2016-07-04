package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.util.Map;

/**
 * Created by wymstar on 7/4/16.
 */
public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String url = "";
    private int contentLength = 0;
    private boolean logined = false;
    private String body = "";


    public HttpRequest(InputStream in) {
        try {
            BufferedReader reader = getBufferedReader(in);
            if (reader == null) {
                return;
            }
            String line = reader.readLine();
            if (line == null) {
                return;
            }

            log.debug("request line : {}", line);
            String[] tokens = line.split(" ");

            while (!line.equals("")) {
                line = reader.readLine();
                log.debug("header : {}", line);

                if (line.contains("Content-Length")) {
                    contentLength = getContentLength(line);
                }

                if (line.contains("Cookie")) {
                    logined = isLogin(line);
                }
            }

            url = getDefaultUrl(tokens);
            body = IOUtils.readData(reader, contentLength);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private BufferedReader getBufferedReader(InputStream in) {
        try {
            return new BufferedReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private int getContentLength(String line) {
        String[] headerTokens = line.split(":");
        return Integer.parseInt(headerTokens[1].trim());
    }

    private String getDefaultUrl(String[] tokens) {
        String url = tokens[1];
        if (url.equals("/")) {
            url = "/index.html";
        }
        return url;
    }

    private boolean isLogin(String line) {
        String[] headerTokens = line.split(":");
        Map<String, String> cookies = HttpRequestUtils.parseCookies(headerTokens[1].trim());
        String value = cookies.get("logined");
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }

    public String getUrl() {
        return url;
    }

    public int getContentLength() {
        return contentLength;
    }

    public boolean isLogined() {
        return logined;
    }

    public String getBody() {
        return body;
    }
}
