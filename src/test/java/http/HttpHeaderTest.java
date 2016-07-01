package http;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wymstar on 6/30/16.
 */
public class HttpHeaderTest {
    HttpHeader header;

    @Before
    public void setup() {
        header = new HttpHeader("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, sdch\n" +
                "Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4\n" +
                "Cookie: JSESSIONID=7703E0693FE97516DE16B15866E75E42");
    }

    @Test
    public void test_method() {
        assertEquals(header.getMethod(), "GET");
    }

    @Test
    public void test_requestURI() {
        assertEquals(header.getRequestURL(), "/index.html");
    }

    @Test
    public void test_httpVersion() {
        assertEquals(header.getHttpVersion(), "HTTP/1.1");
    }

    @Test
    public void test_host() {
        assertEquals(header.getAttribute("Host"), "localhost:8080");
    }

    @Test
    public void test_cookie() {
        assertEquals(header.getAttribute("Cookie"), "JSESSIONID=7703E0693FE97516DE16B15866E75E42");
    }
}
