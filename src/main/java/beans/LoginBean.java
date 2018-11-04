package beans;

import dao.UserDao;
import entities.UserEntity;

import javax.ejb.Stateful;

@Stateful(name = "LoginEJB")
public class LoginBean {
    UserDao userDao = new UserDao();
    private UserEntity loginedUser = userDao.findById(1);

    public LoginBean() {}

    public boolean loginUser(){
        //if login and password are correct, binding login token and UserEntity
        /*
        UserDao userDao = new UserDao();
        UserEntity user = userDao.findById(idFromLoginToken);
        loginedUser=user;
        return true;
        in other condition...
         */
        return false;
    }

    public UserEntity getLoginedUser() {
        return loginedUser;
    }
}
