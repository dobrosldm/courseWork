package beans;

import dao.UserDao;
import entities.UserEntity;
import services.AuthenticationService;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

@Stateless
@Path("profile")
@RolesAllowed({"user", "Ouser"})
public class ProfileBean {

    @Resource
    private SessionContext sessionContext;

    @POST
    public Response changeProfileInfo(@FormParam("name") String name,
                                      @FormParam("surname") String surname,
                                      @FormParam("middlename") String middlename,
                                      @FormParam("sex") String sex,
                                      @FormParam("birthDate") Date birthDate,
                                      @FormParam("mobileTelephone") String mobileTelephone,
                                      @FormParam("preference") String preference) {
        UserDao userDao = new UserDao();
        UserEntity user = userDao.findByEmail(sessionContext.getCallerPrincipal().getName());
        if (name != null && !surname.equals(""))
            user.setName(name);
        if (surname != null && !surname.equals(""))
            user.setSurname(surname);
        if (middlename != null && !middlename.equals(""))
            user.setMiddleName(middlename);
        if (sex.equals("М") || sex.equals("Ж") || sex.equals("Не афишировать")) user.setSex(sex);
//        else user.setSex(""); //todo или ошибку кидать?
        if(birthDate != null)
            user.setBirthDate(birthDate);
        if (mobileTelephone != null && !mobileTelephone.equals(""))
            user.setMobileTelephone(mobileTelephone);
        user.setPreferenceId(Integer.valueOf(preference));

        userDao.update(user);

        return Response.ok("Profile info upadated").build();
    }

    @POST
    @RolesAllowed("user")
    @Path("changePassword")
    public Response register(@FormParam("oldPassword") String oldPassword,
                             @FormParam("firstPassword") String firstPassword,
                             @FormParam("secondPassword") String secondPassword) {
        UserDao userDao = new UserDao();
        UserEntity user = userDao.findByEmail(sessionContext.getCallerPrincipal().getName());

        try {
            if (user.getPassword().equals(AuthenticationService.encodeSHA256(oldPassword))) {
                if (firstPassword.equals(secondPassword)) {

                    try {
                        user.setPassword(AuthenticationService.encodeSHA256(firstPassword));
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("Problems while encoding the password\nAdmins are on their way").build();
                    }

                    userDao.update(user);
                    return Response.ok("Password updated").build();
                } else return Response.status(Response.Status.CONFLICT).entity("Passwords do not match").build();
            } else
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Old password is wrong").build();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Problems while encoding the password\nAdmins are on their way").build();
        }
    }
}
