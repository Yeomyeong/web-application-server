package action;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by wymstar on 7/6/16.
 */
public class LogOutAction implements Action {
    @Override
    public void act(HttpRequest httpRequest, HttpResponse response) {
        response.removeCookie("logined");
        response.redirect("/index.html");
    }
}
