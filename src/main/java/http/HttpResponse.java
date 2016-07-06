package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wymstar on 7/4/16.
 */
public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private Map<String, String> cookies = new LinkedHashMap<>();

    public HttpResponse(OutputStream dos) {
        this.dos = new DataOutputStream(dos);
    }

    public void write (String data) {
        write(data, "");
    }

    public void write (String data, String accept) {
        byte[] body = data.getBytes();
        if (accept.contains("text/css")) {
            responseCSS200Header(body.length);
        } else {
            response200Header(body.length);
        }
        responseBody(body);
    }

    public void redirect (String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: "+ redirectUrl +"\r\n");
            responseSetCookie();
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public void removeCookie(String key) {
        cookies.remove(key);
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            responseSetCookie();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseCSS200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            responseSetCookie();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseSetCookie() {
        try {
            if (cookies.size() > 0) {
                dos.writeBytes("Set-Cookie: " + writeCookies(cookies)+ "\r\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String writeCookies(Map<String, String> cookies) {
        StringBuilder cookieString = new StringBuilder();
        for (String key : cookies.keySet()) {
            String value = cookies.get(key);
            if (cookieString.length() == 0)
                cookieString.append(key).append("=").append(value);
            else
                cookieString.append("; ").append(key).append("=").append(value);
        }
        return cookieString.toString();
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
