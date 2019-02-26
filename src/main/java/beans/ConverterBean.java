package beans;

import dao.GenericDao;
import dao.UserDao;
import entities.*;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Stateless
@Path("converter")
@RolesAllowed("user")
public class ConverterBean {

    @Resource
    private SessionContext sessionContext;

    @POST
    public boolean convert(@FormParam("from") String fromStr,
                           @FormParam("to") String toStr,
                           @FormParam("howMuchTo") String howMuchToStr) {

        int from = Integer.parseInt(fromStr);
        int to = Integer.parseInt(toStr);
        float howMuchTo = Float.parseFloat(howMuchToStr);

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
            todayLimits.finishWork();
            return true;
        } else{
            todayLimits.finishWork();
            return false;
        }
    }
}