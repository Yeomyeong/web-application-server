package action;

import http.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

/**
 * Created by wymstar on 7/1/16.
 */
public interface Action {
    Logger log = LoggerFactory.getLogger(Action.class);

    String act(Request request);
}
