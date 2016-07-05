package webserver;

import action.*;
import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by wymstar on 7/6/16.
 */
public class RequestMapper {
    public static Action createAction(HttpRequest request, HttpResponse response) {
        if (request.getRequestURL().startsWith("/user/create")) {
            return new SignInAction();
        } else if (request.getPath().equals("/user/login")) {
            return new LogInAction();
        } else if (request.getPath().equals("/user/list")) {
            return new UserListAction();
        } else if (request.getPath().equals("/user/logout")) {
            return new LogOutAction();
        } else {
            return new StaticFileReadAction();
        }
    }
}
