package beans;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import dao.GenericDao;
import dao.UserDao;
import entities.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Stateless
@Path("auth")
public class AuthBean {

    @POST
    @Path("vkGetCode")
    public void vkGetCode(@Context HttpServletResponse response, @Context HttpServletRequest request) throws IOException {
        response.sendRedirect("https://oauth.vk.com/authorize?" +
                "client_id=6877006" +
                "&display=page" +
                "&redirect_uri=http://localhost:24315/courseWork/api/auth/vkGetAccessToken" +
                "&scope=email" +
                "&response_type=code" +
                "&v=5.92");
    }

    @GET
    @Path("vkGetAccessToken")
    public String vkGetAccessToken(@Context HttpServletResponse response, @Context HttpServletRequest request) throws IOException {
        response.sendRedirect("https://oauth.vk.com/access_token?" +
                "client_id=6877006" +
                "&client_secret=Uak5frN9r3oE7Fii7fJ9" +
                "&redirect_uri=http://localhost:24315/courseWork/api/auth/vkGetAccessToken" +
                "&code=" + request.getParameter("code"));

        return "HEY!";
    }

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