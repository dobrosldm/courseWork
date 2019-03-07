package beans;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.queries.account.AccountGetProfileInfoQuery;
import dao.GenericDao;
import dao.UserDao;
import entities.*;
import services.AuthenticationService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
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
    @Path("googleCode")
    public void googleCode(@Context HttpServletResponse response, @Context HttpServletRequest request) throws IOException {
        String clientId = "896135202021-huh38ginkjct777ts4jldnm43c0o81lo.apps.googleusercontent.com",
                clientSecret = "asXLAbuN7zIhULMVnUTeMcdK",
                authorizeURL = "https://accounts.google.com/o/oauth2/v2/auth",
                tokenURL = "";
        response.sendRedirect(authorizeURL + "?client_id=896135202021-huh38ginkjct777ts4jldnm43c0o81lo.apps.googleusercontent.com" +
                "&response_type=code&" +
                "scope=email " +
                "openid " +
                "https://www.googleapis.com/auth/userinfo.profile " +
                "https://www.googleapis.com/auth/plus.me " +
                "https://www.googleapis.com/auth/user.phonenumbers.read " +
                "https://www.googleapis.com/auth/user.birthday.read&" +
                "redirect_uri=http://localhost:24315/courseWork/api/auth/googleAuth&" +
                "access_type=offline");
    }

    @GET
    @Path("googleAuth")
    public Response googleAuth(@Context HttpServletResponse response, @Context HttpServletRequest request) throws IOException {
        Form postForm = new Form();
        postForm.param("code", request.getParameter("code"));
        postForm.param("client_id", "896135202021-huh38ginkjct777ts4jldnm43c0o81lo.apps.googleusercontent.com");
        postForm.param("client_secret", "asXLAbuN7zIhULMVnUTeMcdK");
        postForm.param("grant_type", "authorization_code");
        postForm.param("redirect_uri", "http://localhost:24315/courseWork/api/auth/googleAuth");

        Response myResponse = ClientBuilder.newClient().target("https://www.googleapis.com/oauth2/v4/token")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(postForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        JsonObject jsonObject = new Gson().fromJson(myResponse.readEntity(String.class), JsonObject.class);

        myResponse = ClientBuilder.newClient().target("https://people.googleapis.com/v1/people/me" +
                "?personFields=names,birthdays,emailAddresses,genders,phoneNumbers" +
                "&access_token=" + jsonObject.get("access_token").getAsString())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        jsonObject = new Gson().fromJson(myResponse.readEntity(String.class), JsonObject.class);
        String name = jsonObject.get("names").getAsJsonArray().get(0).getAsJsonObject().get("givenName").getAsString();
        String surname = jsonObject.get("names").getAsJsonArray().get(0).getAsJsonObject().get("familyName").getAsString();
        String gender = jsonObject.has("genders") ? (jsonObject.get("genders").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString()
                .equals("male") ? "М" : "Ж") : "";
        Date bDate = new Date(jsonObject.get("birthdays").getAsJsonArray().get(0).getAsJsonObject().get("date").getAsJsonObject().get("year").getAsInt() - 1900,
                jsonObject.get("birthdays").getAsJsonArray().get(0).getAsJsonObject().get("date").getAsJsonObject().get("month").getAsInt() - 1,
                jsonObject.get("birthdays").getAsJsonArray().get(0).getAsJsonObject().get("date").getAsJsonObject().get("day").getAsInt());
        String email = jsonObject.get("emailAddresses").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
        String passwd = jsonObject.get("resourceName").getAsString();
        String phoneNumber;
        if (jsonObject.has("phoneNumbers"))
            phoneNumber = jsonObject.get("phoneNumbers").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
        else phoneNumber = "";
        String preference = "0";

        request.setAttribute("OAuth", "true");
        return register(email, passwd, passwd, name, surname, "", gender, bDate, phoneNumber, preference, response, request);
    }


    @POST
    @Path("vkGetCode")
    public String vkGetCode(@Context HttpServletResponse response, @Context HttpServletRequest request) throws IOException, ClientException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(96644608, "9bc6a784c120ac02bea9fdb061a620a92a5fb95a6176b7f17f84dc26aaa1a281d0cf4332aef705f97c378");

        AccountGetProfileInfoQuery profileInfoQuery = vk.account().getProfileInfo(actor);
        String profileInfo = profileInfoQuery.executeAsString();
        return profileInfo;
    }

    @GET
    @Path("vkAuth")
    public String vkAuth(@Context HttpServletResponse response, @Context HttpServletRequest request) throws ClientException, ApiException {
        String code = request.getParameter("code");

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(6884651, "G4odgeMKW9lnf6bnJo8O",
                        "http://localhost:24315/courseWork/api/auth/vkAuth", code)
                .execute();

        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        AccountGetProfileInfoQuery profileInfoQuery = vk.account().getProfileInfo(actor);
        String profileInfo = profileInfoQuery.executeAsString();
        return profileInfo;
//        return vk.account().getAppPermissions(actor, actor.getId()).executeAsString();
    }

    @POST
    @Path("register")
    public Response register(@FormParam("email") String email,
                           @FormParam("firstPassword") String firstPassword,
                           @FormParam("secondPassword") String secondPassword,
                           @FormParam("name") String name,
                           @FormParam("surname") String surname,
                           @FormParam("middlename") String middlename,
                           @FormParam("sex") String sex,
                           @FormParam("birthDate") Date birthDate,
                           @FormParam("mobileTelephone") String mobileTelephone,
                           @FormParam("preference") String preference,
                           @Context HttpServletResponse response, @Context HttpServletRequest request) throws IOException {

        boolean alreadyExists = true;

        UserDao userDao = new UserDao();
        try {
            userDao.findByEmail(email);
        } catch (NoResultException e) {
            alreadyExists = false;
        }

        if (!firstPassword.equals(secondPassword)) {
            //Response.status(Response.Status.FORBIDDEN).entity("Error!").build();
            return Response.status(Response.Status.CONFLICT).entity("Passwords do not match").build();
        }

        if (alreadyExists &&
                (request.getAttribute("OAuth") == null || !request.getAttribute("OAuth").equals("true"))) {
            return Response.status(Response.Status.CONFLICT).entity("User with this email already exists").build();
        }

        if (!alreadyExists) {
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

            try {
                user.setPassword(AuthenticationService.encodeSHA256(firstPassword));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Problems while encoding the password during registrahun\nAdmins are on their way")
                        .build();
            }

            userDao.create(user);
            GenericDao<UserGroupsEntity, String> userGroupsDao = new GenericDao<>(UserGroupsEntity.class);
            UserGroupsEntity userGroups = new UserGroupsEntity();
            userGroups.setEmail(email);
            userGroups.setGroup((request.getAttribute("OAuth") != null
                    || request.getAttribute("OAuth").equals("true")) ? "Ouser" : "user");
            userGroupsDao.create(userGroups);

            user = userDao.findByEmail(email);
            GenericDao<TodayLimitEntity, TodayLimitEntityPK> todayLimits = new GenericDao<>(TodayLimitEntity.class);
            GenericDao<TransportEntity, Integer> transport = new GenericDao<>(TransportEntity.class);
            for (int i = 0; i < transport.selectAll().size(); i++) {
                todayLimits.create(new TodayLimitEntity(user.getId(), transport.selectAll().get(i).getId(), 1f));
            }

            response.sendRedirect(response.encodeRedirectURL("http://localhost:24315/courseWork/j_security_check?" +
                    "j_username=" + email +
                    "&j_password=" + firstPassword));

            return Response.ok().entity("Successfully registered").build();
        }

        if (request.getAttribute("OAuth") != null && request.getAttribute("OAuth").equals("true"))
            response.sendRedirect(response.encodeRedirectURL("http://localhost:24315/courseWork/j_security_check?" +
                    "j_username=" + email +
                    "&j_password=" + firstPassword));

        return Response.ok().entity("Logged in").build();

        /*Form loginForm = new Form();
        loginForm.param("j_username", email);
        loginForm.param("j_password", firstPassword);
        Response myResponse = ClientBuilder.newClient().target("http://localhost:24315/courseWork/j_security_check")
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(loginForm));
        Map<String, NewCookie> cr = myResponse.getCookies();

        ArrayList<Object> cookies = new ArrayList<>();
        for (NewCookie cookie : cr.values()) {
            cookies.add(cookie.toCookie());
        }*/

        //return Response.status(Response.Status.OK).entity("Registration completed").build();
    }

    @POST
    @Path("logout")
    @RolesAllowed({"user", "Ouser"})
    public void logout(@Context HttpServletResponse response,
                       @Context HttpServletRequest request) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
    }
}