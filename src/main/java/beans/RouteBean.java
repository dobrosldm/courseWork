package beans;

import com.google.gson.JsonObject;
import dao.GenericDao;
import dao.RouteDao;
import dao.TodayLimitDao;
import dao.UserDao;
import entities.TodayLimitEntity;
import entities.TodayLimitEntityPK;
import entities.TransportEntity;
import entities.UserEntity;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("route")
@RolesAllowed("user")
public class RouteBean {

    @Resource
    private SessionContext sessionContext;

    private static final Logger LOG = Logger.getLogger(RouteBean.class.getName());

    @GET
    @Produces("application/json")
    public Response showRoute(@QueryParam("from") String fromStr,
                              @QueryParam("to") String toStr) {

        final String case1 = "Пересаживаемся на другой маршрут";
        final String case2 = "Пересаживаемся на другой номер маршрута";

        final String wrongStpg = "Вы ввели неверные номера остановок";
        final String noRoute = "Увы, невозможно построить маршрут, так как начальный и конечный маршруты не имеют пересечения";

        JsonObject jsonObject = new JsonObject();

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
        List<Object[]> stoppageList = routeDao.createRoute(Integer.valueOf(fromStr), Integer.valueOf(toStr));

        if (stoppageList.get(0)[0].equals(wrongStpg)) {

            jsonObject.addProperty("warning", "wrong stoppages");

        } else if (stoppageList.get(0)[0].equals(noRoute)) {

            jsonObject.addProperty("warning", "route doesn't exist");

        } else {

            int l = 1;
            for (Object[] miniRoute : stoppageList) {
                jsonObject.addProperty("stoppageFrom"+l, miniRoute[0].toString());
                jsonObject.addProperty("stoppageTo"+l, miniRoute[1].toString());
                jsonObject.addProperty("transport"+l, miniRoute[2].toString());
                l++;

                if (!(miniRoute[2].toString().equals(case1) || miniRoute[2].toString().equals(case2))) {

                    for (TransportEntity transport : transportList) {

                        for (int j = 0; j < numberOfTransports; j++) {

                            LOG.log(Level.WARNING, "todayUserLimitPerTransport[j].getTransportId()");
                            LOG.log(Level.WARNING, String.valueOf(todayUserLimitPerTransport[j].getTransportId()));
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

            if (notEnough)
                jsonObject.addProperty("info", "not enough limit for ride :(");
            else {
                TodayLimitEntity[] todayUserLimitPerTransportOld = new TodayLimitEntity[numberOfTransports];
                TodayLimitEntityPK todayLimitEntityPKOld;

                jsonObject.addProperty("spent limits for ride", "(by every transport)");

                for (int q = 0; q < numberOfTransports; q++) {
                    todayLimitEntityPKOld = new TodayLimitEntityPK(user.getId(), q+1);
                    todayUserLimitPerTransportOld[q] = todayLimitDao.findById(todayLimitEntityPKOld);

                    jsonObject.addProperty(String.valueOf(transportList.get(q).getName()), String.valueOf(todayUserLimitPerTransportOld[q].getLimit()-todayUserLimitPerTransport[q].getLimit()));

                    todayLimitDao.update(todayUserLimitPerTransport[q]);
                }

                jsonObject.addProperty("info", "thanks for ride!");
            }
        }

        return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();
    }
}