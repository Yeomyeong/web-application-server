package action;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by wymstar on 7/1/16.
 */
public interface Action {
    void act(HttpRequest httpRequest, HttpResponse response);
}
