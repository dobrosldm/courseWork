package beans;

import dao.UserDao;
import entities.UserEntity;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Stateless
@Path("auth")
public class AuthBean {
    /*UserDao userDao = new UserDao();
    private UserEntity loginedUser = userDao.findById(1);*/

    public AuthBean() {}

    @POST
    @Path("login")
    public String loginUser(@FormParam("login") String login, @FormParam("password") String password,
                             @Context HttpServletResponse resp, @Context HttpServletRequest req){
        try {
            req.login(login, password);
        } catch (ServletException se) {
            return "nea";
        }

        return "aga";
    }

    /*public UserEntity getLoginedUser() {
        return loginedUser;
    }*/
}
