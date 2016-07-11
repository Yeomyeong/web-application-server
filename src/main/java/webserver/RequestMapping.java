package webserver;

import action.*;
import http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wymstar on 7/6/16.
 */
public class RequestMapping {
    private Map<String, Action> requestMap = new HashMap<>();
    private Action defaulAction = new StaticFileReadAction();

    public RequestMapping() {
        requestMap.put("/user/create", new SignInAction());
        requestMap.put("/user/login", new LogInAction());
        requestMap.put("/user/list", new UserListAction());
        requestMap.put("/user/logout", new LogOutAction());
    }

    public Action getAction (HttpRequest request) {
        Action action = requestMap.get(request.getPath());
        if (action == null)
            return defaulAction;
        return action;

    }
}
