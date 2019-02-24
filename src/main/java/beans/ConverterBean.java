package beans;

import dao.*;
import entities.*;

import javax.ejb.Stateful;

@Stateful(name = "ConverterEJB")
public class ConverterBean {
    AuthBean authBean;

    public ConverterBean() {
        authBean = new AuthBean();
    }

    /*public boolean convert(int from, int to, float howMuchTo){
        UserEntity user = authBean.getLoginedUser();
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
    }*/
}
