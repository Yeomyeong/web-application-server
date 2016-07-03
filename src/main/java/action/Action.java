package action;

import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wymstar on 7/1/16.
 */
public interface Action {
    Logger log = LoggerFactory.getLogger(Action.class);

    String act(HttpRequest httpRequest);
}
