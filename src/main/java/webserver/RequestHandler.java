package webserver;

import java.io.*;
import java.net.Socket;

import action.SignInAction;
import action.StaticFileReadAction;
import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            HttpRequest httpRequest = new HttpRequest(in);
            //log.debug(httpHeader.toString());

            String responseData;
            if (httpRequest.getRequestURL().startsWith("/user/create")) {
                responseData = new SignInAction().act(httpRequest);
            } else {
                responseData = new StaticFileReadAction().act(httpRequest);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = responseData.getBytes();
            response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
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
