package action;

import db.DataBase;
import http.Request;
import model.User;

import static util.StringUtil.decode;
import static util.StringUtil.isEmpty;

/**
 * Created by wymstar on 7/1/16.
 */
public class SignInAction implements Action {

    @Override
    public String act(Request request) {
        if ( isEmpty(request.getParameter("userId")) ||
                isEmpty(request.getParameter("password")) ||
                isEmpty(request.getParameter("name")) ||
                isEmpty(request.getParameter("email"))) {
            return "회원 가입이 실패하였습니다.";
        }

        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                decode(request.getParameter("name")),
                request.getParameter("email"));
        DataBase.addUser(user);
        return  user.getName() + "님 회원 가입이 성공하였습니다.";

    }
}
