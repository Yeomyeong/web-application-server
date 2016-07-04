package action;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

import static util.StringUtil.decode;
import static util.StringUtil.isEmpty;

/**
 * Created by wymstar on 7/1/16.
 */
public class SignInAction implements Action {

    @Override
    public void act(HttpRequest request, HttpResponse response) {
        if (isValidSignIn(request)) {
            signIn(request);
            response.redirect("/index.html");
        }
    }

    private void signIn(HttpRequest request) {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                decode(request.getParameter("name")),
                request.getParameter("email"));
        DataBase.addUser(user);
    }

    private boolean isValidSignIn(HttpRequest request) {
        if ( isEmpty(request.getParameter("userId")) ||
                isEmpty(request.getParameter("password")) ||
                isEmpty(request.getParameter("name")) ||
                isEmpty(request.getParameter("email"))) {
            return false;
        }
        return true;
    }
}
