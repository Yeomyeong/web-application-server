package webserver;

import java.io.*;
import java.net.Socket;

import action.*;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	
	private Socket connection;
    private RequestMapping requestMapping;

	public RequestHandler(Socket connectionSocket, RequestMapping requestMapping) {
		this.connection = connectionSocket;
        this.requestMapping = requestMapping;
	}

	@Override
	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}",
				connection.getInetAddress(),
				connection.getPort());

		try ( InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
			HttpResponse response = new HttpResponse(out);

            Action action = requestMapping.getAction(request);
            action.act(request, response);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
