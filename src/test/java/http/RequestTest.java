package http;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wymstar on 6/30/16.
 */
public class RequestTest {
    Request request;

    @Before
    public void setup() {
        request = new Request("/user/create?userId=wym&password=1234&name=우여명&email=wym@mail.com");
    }

    @Test
    public void test_path() {
        assertEquals(request.getPath(), "/user/create");
    }

    @Test
    public void test_parameter1() {
        assertEquals(request.getParameter("userId"), "wym");
    }
}
