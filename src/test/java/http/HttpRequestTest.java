package http;

import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by wymstar on 7/4/16.
 */
public class HttpRequestTest {
    @Test
    public void loginTest() throws Exception{
        HttpRequest request = new HttpRequest(new FileInputStream("./src/test/resources/login-header.txt"));

        assertEquals(request.getMethod(), "POST");
        assertEquals(request.getUrl(), "/user/login");
        assertEquals(request.getHeader("Content-Length"), "25");
        assertEquals(request.getHeader("Connection"), "keep-alive");

    }
}
