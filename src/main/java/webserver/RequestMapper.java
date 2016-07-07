package webserver;

import action.*;
import http.HttpRequest;

/**
 * Created by wymstar on 7/6/16.
 */
public class RequestMapper {
    private RequestMapper() {}

    public static Action createAction(HttpRequest request) {
        if ("/user/create".equals(request.getPath())) {
            return new SignInAction();
        } else if ("/user/login".equals(request.getPath())) {
            return new LogInAction();
        } else if ("/user/list".equals(request.getPath())) {
            return new UserListAction();
        } else if ("/user/logout".equals(request.getPath())) {
            return new LogOutAction();
        } else {
            return new StaticFileReadAction();
        }
    }
}
