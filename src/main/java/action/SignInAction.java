package action;

import db.DataBase;
import http.HttpRequest;
import model.User;

import static util.StringUtil.decode;
import static util.StringUtil.isEmpty;

/**
 * Created by wymstar on 7/1/16.
 */
public class SignInAction implements Action {

    @Override
    public String act(HttpRequest httpRequest) {
        if ( isEmpty(httpRequest.getParameter("userId")) ||
                isEmpty(httpRequest.getParameter("password")) ||
                isEmpty(httpRequest.getParameter("name")) ||
                isEmpty(httpRequest.getParameter("email"))) {
            return "회원 가입이 실패하였습니다.";
        }

        User user = new User(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                decode(httpRequest.getParameter("name")),
                httpRequest.getParameter("email"));
        DataBase.addUser(user);
        return  user.getName() + "님 회원 가입이 성공하였습니다.";

    }
}
