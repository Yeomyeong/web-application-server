package http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testhelper.TestUtil;

import java.io.FileOutputStream;

/**
 * Created by aymonwoo on 2016. 7. 6..
 */
public class HttpResponseTest {
    private HttpResponse response;
    private String filename = "./src/test/resources/http/response/httpResponse.txt";

    @Before
    public void setup () throws Exception {
        response = new HttpResponse(new FileOutputStream(filename));
    }

    @Test
    public void test_write() throws Exception {
        response.write("Good?");

        Assert.assertEquals(
                "HTTP/1.1 200 OK \r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "Content-Length: 5\r\n" +
                "\r\n" +
                "Good?" +
                "\r\n",
                TestUtil.readFile(filename));
    }

    @Test
    public void test_setCookie() throws Exception {
        response.addCookie("logined", "true");
        response.write("Good?");

        Assert.assertEquals(
                "HTTP/1.1 200 OK \r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Content-Length: 5\r\n" +
                        "Set-Cookie: logined=true\r\n" +
                        "\r\n" +
                        "Good?" +
                        "\r\n",
                TestUtil.readFile(filename));
    }

    @Test
    public void test_setCookie2() throws Exception {
        response.addCookie("logined", "true");
        response.addCookie("displayAd", "true");
        response.addCookie("userType", "common");
        response.write("Good?");

        Assert.assertEquals(
                "HTTP/1.1 200 OK \r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Content-Length: 5\r\n" +
                        "Set-Cookie: logined=true; displayAd=true; userType=common\r\n" +
                        "\r\n" +
                        "Good?" +
                        "\r\n",
                TestUtil.readFile(filename));
    }

    @Test
    public void test_removeCookie() throws Exception {
        response.addCookie("logined", "true");
        response.addCookie("displayAd", "true");
        response.addCookie("userType", "common");
        response.removeCookie("userType");

        response.write("Good?");

        Assert.assertEquals(
                "HTTP/1.1 200 OK \r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Content-Length: 5\r\n" +
                        "Set-Cookie: logined=true; displayAd=true\r\n" +
                        "\r\n" +
                        "Good?" +
                        "\r\n",
                TestUtil.readFile(filename));
    }

    @Test
    public void test_writeCss() throws Exception {
        String accept = "text/css,*/*;q=0.1";
        response.write("Good?", accept);

        Assert.assertEquals(
                "HTTP/1.1 200 OK \r\n" +
                        "Content-Type: text/css;charset=utf-8\r\n" +
                        "Content-Length: 5\r\n" +
                        "\r\n" +
                        "Good?" +
                        "\r\n",
                TestUtil.readFile(filename));
    }

    @Test
    public void test_redirectTest() throws Exception {
        response.redirect("/index.html");

        Assert.assertEquals(
                "HTTP/1.1 302 Found \r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n",
                TestUtil.readFile(filename));
    }

}
