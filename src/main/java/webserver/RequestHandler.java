package webserver;

import java.io.*;
import java.net.Socket;

import action.LogInAction;
import action.SignInAction;
import action.StaticFileReadAction;
import http.HttpRequest;
import http.HttpResponse;
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
			HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.getRequestURL().startsWith("/user/create")) {
                new SignInAction().act(httpRequest, httpResponse);
            } else if (httpRequest.getPath().equals("/user/login")) {
                new LogInAction().act(httpRequest, httpResponse);
            } else {
                new StaticFileReadAction().act(httpRequest, httpResponse);
            }

		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
