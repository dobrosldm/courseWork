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

@Stateless
@Path("auth")
public class RegistrationBean {
    public RegistrationBean() {
    }

    //String name, String surname, String middleName, String email, String sex, String birthDateStr, String mobileTelephone, boolean disability, short familySize

    @POST
    @Path("register")
    public String register(@FormParam("email") String email, @FormParam("firstPassword") String firstPassword,
                           @FormParam("secondPassword") String secondPassword,
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
            return "nop";
        }



        UserEntity user = new UserEntity();
        user.setId(userDao.selectAll().size()+1);
        //user.setName(name);
        //user.setSurname(surname);
        //if (middleName != null) user.setMiddleName(middleName);
        user.setEmail(email);
        user.setPassword(firstPassword);
        //user.setSex(sex);
        //user.setBirthDate(Date.valueOf(birthDateStr));
        //if (mobileTelephone != null) user.setMobileTelephone(mobileTelephone);
        //user.setDisability(disability);
        //user.setFamilySize(familySize);

        userDao.create(user);

        return "yep";
        //return Response.status(Response.Status.OK).entity("Registration completed").build();
    }

    @GET
    @Path("logout")
    @RolesAllowed({"users"})
    public void logout(@Context HttpServletResponse response,
                       @Context HttpServletRequest request) throws IOException
    {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
    }
}
