package beans;

import dao.UserDao;
import entities.UserEntity;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.sql.Date;

@Stateless
@Path("auth")
public class AuthBean {
    public AuthBean() {
    }

    //String name, String surname, String middleName, String email, String sex, String birthDateStr, String mobileTelephone, boolean disability, short familySize

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
            return "nea";
        }



        UserEntity user = new UserEntity();
        //user.setId(userDao.selectAll().size()+1);
        user.setName(name);
        user.setSurname(surname);
        if (middlename != null)
            user.setMiddleName(middlename);
        user.setEmail(email);
        user.setPassword(firstPassword);
        user.setSex(sex);
        user.setBirthDate(birthDate);
        if (mobileTelephone != null)
            user.setMobileTelephone(mobileTelephone);


        // TODO: change
        user.setPreferenceId(3);

        userDao.create(user);

        return "aga";
        //return Response.status(Response.Status.OK).entity("Registration completed").build();
    }

    @POST
    @Path("logout")
    @RolesAllowed({"users"})
    public void logout(@Context HttpServletResponse response,
                       @Context HttpServletRequest request) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
    }
}
