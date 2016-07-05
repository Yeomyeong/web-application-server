package action;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

/**
 * Created by wymstar on 7/6/16.
 */
public class UserListAction implements Action{
    @Override
    public void act(HttpRequest request, HttpResponse response) {
        if ( !"true".equals(request.getCookie("logined")) ) {
            response.redirect("/user/login.html");
            return ;
        }
        response.write( getUserList() );
    }

    private String getUserList() {
        StringBuilder userListHtml = new StringBuilder();

        userListHtml.append("<h2>사용자 리스트</h2>");
        for (User user : DataBase.findAll()) {
            userListHtml.append("UserId : ").append(user.getUserId()).append("<br>")
                    .append("Name : ").append(user.getName()).append("<br>")
                    .append("E-mail : ").append(user.getEmail()).append("<br>");
            userListHtml.append("<br>");
        }

        return userListHtml.toString();
    }
}
