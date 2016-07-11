package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static util.StringUtil.isEmpty;

/**
 * Created by wymstar on 7/7/16.
 */
public class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

    private HttpMethod method;
    private String requestURL;
    private String httpVersion;

    public RequestLine(String requestLine) {
        if (isEmpty(requestLine))
            throw new IllegalHttpHeaderException();

        String[] tokens = requestLine.split(" ");
        log.debug(requestLine + "\n");

        this.method = HttpMethod.getMethod(tokens[0].trim());
        this.requestURL = tokens[1].trim();
        this.httpVersion = tokens[2].trim();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
