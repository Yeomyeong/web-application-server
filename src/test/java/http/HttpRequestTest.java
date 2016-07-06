package http;

import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.StringUtil.decode;

/**
 * Created by wymstar on 6/30/16.
 */
public class HttpRequestTest {
    HttpRequest request;

    @Test
    public void test_GET() throws Exception{
        request = new HttpRequest(new FileInputStream("./src/test/resources/http/request/httpRequest_GET.txt"));
        assertEquals(request.getMethod(), "GET");
        assertEquals(request.getPath(), "/index.html");
        assertEquals(request.getHttpVersion(), "HTTP/1.1");
        assertEquals(request.getHeader("Host"), "localhost:8080");
        assertEquals(request.getCookie("JSESSIONID"), "7703E0693FE97516DE16B15866E75E42");
        assertTrue(request.getHeader("Accept").contains("text/html"));

    }

    @Test
    public void test_POST() throws Exception{
        request = new HttpRequest(new FileInputStream("./src/test/resources/http/request/httpRequest_POST.txt"));
        assertEquals(request.getMethod(), "POST");
        assertEquals(request.getPath(), "/user/create");
        assertEquals(request.getHttpVersion(), "HTTP/1.1");
        assertEquals(request.getHeader("Host"), "localhost:8080");
        assertEquals(request.getCookie("_ga"), "GA1.1.1738595458.1446798258");

        assertEquals(request.getParameter("userId"), "aaaa");
        assertEquals(decode(request.getParameter("password")), "1234");

    }

    @Test
    public void test_CSS() throws Exception{
        request = new HttpRequest(new FileInputStream("./src/test/resources/http/request/httpRequest_CSS.txt"));
        assertEquals(request.getMethod(), "GET");
        assertEquals(request.getPath(), "/css/styles.css");
        assertTrue(request.getHeader("Accept").contains("text/css"));
    }
}
