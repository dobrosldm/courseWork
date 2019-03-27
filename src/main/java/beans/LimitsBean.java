package beans;

import com.google.gson.JsonArray;
import requestHelpers.ConvertInfo;
import com.google.gson.JsonObject;
import dao.GenericDao;
import dao.TodayLimitDao;
import dao.UserDao;
import entities.*;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("limits")
@RolesAllowed({"user", "Ouser"})
public class LimitsBean {

    @Resource
    private SessionContext sessionContext;

    @Path("convert")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response convert(final ConvertInfo input) {

        int from = Integer.parseInt(input.from);
        int to = Integer.parseInt(input.to);
        float howMuchTo = Float.parseFloat(input.howMuchTo);

        UserDao userDao = new UserDao();
        UserEntity user = userDao.findByEmail(sessionContext.getCallerPrincipal().getName());
        GenericDao<TodayLimitEntity, TodayLimitEntityPK> todayLimits = new GenericDao<>(TodayLimitEntity.class);
        GenericDao<ConverterEntity, ConverterEntityPK> converter = new GenericDao<>(ConverterEntity.class);

        float limitFrom = todayLimits.findById(new TodayLimitEntityPK(user.getId(), from)).getLimit();
        float limitTo = todayLimits.findById(new TodayLimitEntityPK(user.getId(), to)).getLimit();
        float ratio = converter.findById(new ConverterEntityPK(from, to)).getRatio();

        if (howMuchTo <= limitFrom*ratio){
            TodayLimitEntity tmpEntity = todayLimits.findById(new TodayLimitEntityPK(user.getId(), from));
            tmpEntity.setLimit(limitFrom-howMuchTo/ratio);
            todayLimits.update(tmpEntity);
            tmpEntity = todayLimits.findById(new TodayLimitEntityPK(user.getId(), to));
            tmpEntity.setLimit(limitTo+howMuchTo);
            todayLimits.update(tmpEntity);
            /*todayLimits.finishWork();*/
            return Response.ok("Successfully converted").build();
        } else{
            /*todayLimits.finishWork();*/
            return Response.ok("Lack of limits").build();
        }
    }

    @GET
    public Response showLimits() {
        UserDao userDao = new UserDao();
        UserEntity user = userDao.findByEmail(sessionContext.getCallerPrincipal().getName());
        TodayLimitDao limitDao = new TodayLimitDao();
        JsonArray jsonLimits = new JsonArray();
        for (Object[] limit: limitDao.findByUserId(user.getId())) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("transport", limit[0].toString());
            jsonObject.addProperty("limit", limit[1].toString());
            jsonLimits.add(jsonObject);
        }
        return Response.ok(jsonLimits.toString(), MediaType.APPLICATION_JSON).build();
    }
}