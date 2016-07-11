package http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wymstar on 7/7/16.
 */
public class RequestLineTest {

    @Test
    public void test_GET() throws Exception {
        String requestLineText = "GET /index.html HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestLineText);

        assertEquals(HttpMethod.GET, requestLine.getMethod());
        assertEquals("path", requestLine.getRequestURL());
        assertEquals("path", requestLine.getHttpVersion());
    }
}
