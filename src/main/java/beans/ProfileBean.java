package beans;

import com.google.gson.JsonObject;
import dao.UserDao;
import entities.UserEntity;
import requestHelpers.ProfileDataInfo;
import requestHelpers.ProfilePasswordInfo;
import services.AuthenticationService;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

    @GET
    public Response getProfileInfo() {
        UserDao userDao = new UserDao();
        UserEntity user = userDao.findByEmail(sessionContext.getCallerPrincipal().getName());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", user.getName());
        jsonObject.addProperty("surname", user.getSurname());
        jsonObject.addProperty("middlename", user.getMiddleName());
        jsonObject.addProperty("gender", user.getSex());
        jsonObject.addProperty("birthDate", user.getBirthDate().toString());
        jsonObject.addProperty("mobileTelephone", user.getMobileTelephone());
        jsonObject.addProperty("preference", user.getPreferenceId());

        return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeProfileInfo(final ProfileDataInfo input) {
        UserDao userDao = new UserDao();
        UserEntity user = userDao.findByEmail(sessionContext.getCallerPrincipal().getName());
        if (input.name != null && !input.name.equals(""))
            user.setName(input.name);
        if (input.surname != null && !input.surname.equals(""))
            user.setSurname(input.surname);
        if (input.middlename != null && !input.middlename.equals(""))
            user.setMiddleName(input.middlename);
        if (input.sex.equals("М") || input.sex.equals("Ж") || input.sex.equals("Не афишировать")) user.setSex(input.sex);
//        else user.setSex(""); //todo или ошибку кидать?
        if(input.birthDate != null)
            user.setBirthDate(input.birthDate);
        if (input.mobileTelephone != null && !input.mobileTelephone.equals(""))
            user.setMobileTelephone(input.mobileTelephone);
        user.setPreferenceId(Integer.valueOf(input.preference));

        userDao.update(user);

        return Response.ok("Profile info updated").build();
    }

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("changePassword")
    public Response register(final ProfilePasswordInfo input) {
        UserDao userDao = new UserDao();
        UserEntity user = userDao.findByEmail(sessionContext.getCallerPrincipal().getName());

        try {
            if (user.getPassword().equals(AuthenticationService.encodeSHA256(input.oldPassword))) {
                if (input.firstPassword.equals(input.secondPassword)) {

                    try {
                        user.setPassword(AuthenticationService.encodeSHA256(input.firstPassword));
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
