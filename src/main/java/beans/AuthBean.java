package beans;

import dao.GenericDao;
import dao.UserDao;
import entities.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

@Stateless
@Path("auth")
public class AuthBean {

    @POST
    @Path("register")
    public String register(@FormParam("email") String email,
                           @FormParam("firstPassword") String firstPassword,
                           @FormParam("secondPassword") String secondPassword,
                           @FormParam("name") String name,
                           @FormParam("surname") String surname,
                           @FormParam("middlename") String middlename,
                           @FormParam("sex") String sex,
                           @FormParam("birthDate") Date birthDate,
                           @FormParam("mobileTelephone") String mobileTelephone,
                           @FormParam("disability") boolean disability,
                           @FormParam("familySize") short familySize,
                           @FormParam("preference") String preference,
                           @Context HttpServletResponse resp, @Context HttpServletRequest req) {

        boolean alreadyExists = true;

        UserDao userDao = new UserDao();
        try {
            userDao.findByEmail(email);
        } catch (NoResultException e) {
            alreadyExists = false;
        }

        if (alreadyExists || !firstPassword.equals(secondPassword)) {
            //Response.status(Response.Status.FORBIDDEN).entity("Error!").build();
            return "Error while register!";
        }



        UserEntity user = new UserEntity();
        //user.setId(userDao.selectAll().size());
        user.setName(name);
        user.setSurname(surname);
        if (middlename != null)
            user.setMiddleName(middlename);
        user.setEmail(email);
        user.setSex(sex);
        user.setBirthDate(birthDate);
        if (mobileTelephone != null)
            user.setMobileTelephone(mobileTelephone);
        user.setPreferenceId(Integer.valueOf(preference));

        GenericDao<UserGroupsEntity, String> userGroupsDao = new GenericDao<>(UserGroupsEntity.class);
        UserGroupsEntity userGroups = new UserGroupsEntity();
        userGroups.setEmail(email);
        userGroups.setGroup("user");
        userGroupsDao.create(userGroups);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(firstPassword.getBytes("UTF-8"));
            byte[] digest = md.digest();
            user.setPassword(DatatypeConverter.printBase64Binary(digest));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        userDao.create(user);

        user = userDao.findByEmail(email);
        GenericDao<TodayLimitEntity, TodayLimitEntityPK> todayLimits = new GenericDao<>(TodayLimitEntity.class);
        GenericDao<TransportEntity, Integer> transport = new GenericDao<>(TransportEntity.class);
        for (int i = 0; i < transport.selectAll().size(); i++) {
            todayLimits.create(new TodayLimitEntity(user.getId(), transport.selectAll().get(i).getId(), 1f));
        }

        return "Successfully registered!";
        //return Response.status(Response.Status.OK).entity("Registration completed").build();
    }

    @POST
    @Path("logout")
    @RolesAllowed({"user"})
    public void logout(@Context HttpServletResponse response,
                       @Context HttpServletRequest request) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
    }
}
