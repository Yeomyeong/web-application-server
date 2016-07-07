package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import util.Pair;
import util.StringUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static http.HttpMethod.GET;
import static http.HttpMethod.POST;
import static util.StringUtil.*;

/**
 * Created by wymstar on 6/30/16.
 */
public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String requestURL = "";
    private HttpMethod method = HttpMethod.GET;
    private String httpVersion = "";
    private String path = "";
    private String data = "";

    private Map<String, String> cookies = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
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
            parseHttpRequestLineAt1st(reader);
            parseHttpHeadersAt2nd(reader);
            parseHttpRequestPathAndDataAt3rd(reader);
            parseRequestParameterAt4rd();
            parseCookieAt4rd();

        } catch ( IOException e) {
            log.error(this.toString() + e.getMessage(), e);
        } catch (RuntimeException e) {
            log.error(this.toString() + e.getMessage(), e);
            throw new IllegalHttpHeaderException();
        }
    }

    private void parseHttpRequestLineAt1st(BufferedReader reader) throws IOException {
        String firstLine = reader.readLine();
        if (isEmpty(firstLine))
            throw new IllegalHttpHeaderException();

        String[] tokens = firstLine.split(" ");
        log.debug(firstLine + "\n");

        this.method = HttpMethod.getMethod(tokens[0].trim());
        this.requestURL = tokens[1].trim();
        this.httpVersion = tokens[2].trim();
    }

    private void parseHttpHeadersAt2nd(BufferedReader reader) throws IOException {
        String line;
        while (isNotEmpty(line = reader.readLine()) ) {
            log.debug(line + "\n");
            Pair header = StringUtil.seperateBy(line, ":");
            if (header != null) {
                this.headers.put(header.getKey().trim(), header.getValue().trim());
            }
        }
    }

    private void parseHttpRequestPathAndDataAt3rd(BufferedReader reader) throws IOException {
        this.path = extractPath();
        if (POST.equals(method)) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            this.data = IOUtils.readData(reader, contentLength);
        } else if (GET.equals(method)) {
            int splitIndex = requestURL.indexOf('?');
            if (splitIndex > 0) {
                this.path = path.substring(0, splitIndex);
                this.data = path.substring(splitIndex + 1);
            }
        }
        log.debug(this.data);
    }

    private String extractPath() {
        String host = headers.get("Host");
        return requestURL.replaceAll("http[s]?://", "").replaceAll(host, "");
    }

    private void parseRequestParameterAt4rd() {
        String[] parameterArray = this.data.split("&");
        for (String keyValueArr : parameterArray) {
            String[] keyValue = keyValueArr.split("=");
            if (keyValue.length == 2) {
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    private void parseCookieAt4rd() {
        String rawCookies = nvl(headers.get("Cookie"));
        for (String rawCookie : rawCookies.split(";")) {
            Pair pair = seperateBy(rawCookie, "=");
            if (pair != null)
                cookies.put(pair.getKey(), pair.getValue());
        }

    }
    public String getRequestURL() {
        return requestURL;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    @Override
    public String toString() {
        return "METHOD : " + method.name() + "\n"
                + "REQUEST URL : " + requestURL  + "\n"
                + "HTTP VERSION : " + httpVersion + "\n"
                + headers.toString();
    }
}
