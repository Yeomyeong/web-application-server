package webserver;

import java.io.*;
import java.net.Socket;

import action.SignInAction;
import action.StaticFileReadAction;
import db.DataBase;
import http.HttpHeader;
import http.Request;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static util.StringUtil.*;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}",
				connection.getInetAddress(),
				connection.getPort());

		try ( InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpHeader httpHeader = new HttpHeader(getHttpHeader(in));
            //log.debug(httpHeader.toString());

            String responseData;
            Request request = new Request(httpHeader.getRequestURL());
            if (httpHeader.getRequestURL().startsWith("/user/create")) {
                responseData = new SignInAction().act(request);
            } else {
                responseData = new StaticFileReadAction().act(request);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = responseData.getBytes();
            response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

    private String getHttpHeader(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder httpHeaderBuffer = new StringBuilder();
        String line;
        while ( isNotEmpty(line = reader.readLine()) ) {
            httpHeaderBuffer.append(line + "\n");
        }
        return httpHeaderBuffer.toString();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
