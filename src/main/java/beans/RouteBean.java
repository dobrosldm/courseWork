package beans;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.GenericDao;
import dao.RouteDao;
import dao.TodayLimitDao;
import dao.UserDao;
import entities.*;
import requestHelpers.RouteInfo;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("route")
@RolesAllowed({"user", "Ouser"})
public class RouteBean {

    @Resource
    private SessionContext sessionContext;

    private static final Logger LOG = Logger.getLogger(RouteBean.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response showRoute(final RouteInfo input) {
        final String case1 = "Пересаживаемся на другой маршрут";
        final String case2 = "Пересаживаемся на другой номер маршрута";

        final String wrongStpg = "Вы ввели неверные номера остановок";
        final String noRoute = "Увы, невозможно построить маршрут, так как начальный и конечный маршруты не имеют пересечения";

        JsonArray jsonArray = new JsonArray();

        UserEntity user = new UserDao().findByEmail(sessionContext.getCallerPrincipal().getName());

        List<TransportEntity> transportList = new GenericDao<TransportEntity, Integer>(TransportEntity.class).selectAll();
        int numberOfTransports = transportList.size();

        TodayLimitEntity[] todayUserLimitPerTransport = new TodayLimitEntity[numberOfTransports];
        TodayLimitEntityPK todayLimitEntityPK;
        GenericDao<TodayLimitEntity, TodayLimitEntityPK> todayLimitDao = new GenericDao<>(TodayLimitEntity.class);

        for (int i = 0; i < numberOfTransports; i++) {
            todayLimitEntityPK = new TodayLimitEntityPK(user.getId(), i+1);
            todayUserLimitPerTransport[i] = todayLimitDao.findById(todayLimitEntityPK);
        }

        RouteDao routeDao = new RouteDao();
        List<Object[]> stoppageList = routeDao.createRoute(Integer.valueOf(input.from), Integer.valueOf(input.to));

        if (stoppageList.get(0)[0].equals(wrongStpg)) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("responseType", "warning");
            jsonObject.addProperty("text", "wrong stoppages");
            jsonArray.add(jsonObject);

        } else if (stoppageList.get(0)[0].equals(noRoute)) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("responseType", "warning");
            jsonObject.addProperty("text", "route doesn't exist");
            jsonArray.add(jsonObject);

        } else {

            JsonArray routesArray = new JsonArray();

            GenericDao<StoppageEntity, Integer> stoppageDao = new GenericDao<>(StoppageEntity.class);
            StoppageEntity stoppageEntity;
            for (Object[] miniRoute : stoppageList) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("stoppageFrom", miniRoute[0].toString());
                stoppageEntity = stoppageDao.findById(Integer.valueOf(miniRoute[0].toString()));
                jsonObject.addProperty("xFrom", stoppageEntity.getXCoord());
                jsonObject.addProperty("yFrom", stoppageEntity.getYCoord());
                jsonObject.addProperty("stoppageTo", miniRoute[1].toString());
                stoppageEntity = stoppageDao.findById(Integer.valueOf(miniRoute[1].toString()));
                jsonObject.addProperty("xTo", stoppageEntity.getXCoord());
                jsonObject.addProperty("yTo", stoppageEntity.getYCoord());
                jsonObject.addProperty("transport", miniRoute[2].toString());
                routesArray.add(jsonObject);

                if (!(miniRoute[2].toString().equals(case1) || miniRoute[2].toString().equals(case2))) {

                    for (TransportEntity transport : transportList) {

                        for (int j = 0; j < numberOfTransports; j++) {

                            if (transport.getName().equals(miniRoute[2].toString())
                                    && (transport.getId() == todayUserLimitPerTransport[j].getTransportId()))
                                todayUserLimitPerTransport[j].setLimit(todayUserLimitPerTransport[j].getLimit() - 1);

                        }
                    }
                }
            }

            boolean notEnough = false;

            for (int k = 0; k < numberOfTransports; k++) {
                if (todayUserLimitPerTransport[k].getLimit() < 0)
                    notEnough = true;
            }

            if (notEnough){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("responseType", "info");
                jsonObject.addProperty("text", "not enough limit");
                jsonArray.add(jsonObject);

                jsonArray.add(routesArray);
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("responseType", "info");
                jsonObject.addProperty("text", "enough limit");
                jsonArray.add(jsonObject);

                jsonArray.add(routesArray);
            }

                TodayLimitEntity[] todayUserLimitPerTransportOld = new TodayLimitEntity[numberOfTransports];
                TodayLimitEntityPK todayLimitEntityPKOld;

                JsonArray paysArray = new JsonArray();

                for (int q = 0; q < numberOfTransports; q++) {
                    todayLimitEntityPKOld = new TodayLimitEntityPK(user.getId(), q + 1);
                    todayUserLimitPerTransportOld[q] = todayLimitDao.findById(todayLimitEntityPKOld);

                    JsonObject payObject = new JsonObject();
                    payObject.addProperty("transport", String.valueOf(transportList.get(q).getName()));
                    payObject.addProperty("spent", String.valueOf(todayUserLimitPerTransportOld[q].getLimit() - todayUserLimitPerTransport[q].getLimit()));
                    paysArray.add(payObject);

                    if (input.spend.equals("yes"))
                        todayLimitDao.update(todayUserLimitPerTransport[q]);
                }

                jsonArray.add(paysArray);
        }

        return Response.ok(jsonArray.toString(), MediaType.APPLICATION_JSON).build();
    }
}