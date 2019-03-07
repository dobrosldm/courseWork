package dao;

import entities.TodayLimitEntity;
import entities.TodayLimitEntityPK;

import javax.persistence.EntityManager;
import java.util.List;

public class TodayLimitDao extends GenericDao<TodayLimitEntity, TodayLimitEntityPK> {
    public TodayLimitDao() {
        super(TodayLimitEntity.class);
    }

    public List<Object[]> findByUserId(int userId) {
        EntityManager entityManager = getEntityManager();

        List<Object[]> list = entityManager.createQuery("select transport.name, todayLimit.limit from TodayLimitEntity todayLimit " +
                "join TransportEntity transport on (todayLimit.transportId=transport.id and todayLimit.userId=:userId)")
                .setParameter("userId", userId).getResultList();

        entityManager.close();
        return list;
    }
}
