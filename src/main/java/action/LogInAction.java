package action;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

import static action.ActionUtil.readFile;
import static util.StringUtil.nvl;

/**
 * Created by wymstar on 7/1/16.
 */
public class LogInAction implements Action {

    @Override
    public void act(HttpRequest request, HttpResponse response) {
        String id = nvl(request.getParameter("userId"));
        String password = nvl(request.getParameter("password"));

        User user = DataBase.findUserById(id);
        if (user == null ) {
            loginFailed(response);
            return ;
        }

        if(! password.equals(user.getPassword())) {
            loginFailed(response);
            return ;
        }

        response.addCookie("logined", "true");
        response.redirect("/index.html");
    }

    private void loginFailed(HttpResponse response) {
        response.addCookie("logined", "false");
        readFile(response, "./webapp/user/login_failed.html");
    }

}
