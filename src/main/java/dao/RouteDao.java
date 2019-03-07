package dao;

import entities.RouteEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class RouteDao extends GenericDao<RouteEntity, Integer> {

    private EntityManager entityManager;

    public RouteDao() {
        super(RouteEntity.class);
    }

    public List<Object[]> createRoute(int from, int to) {

        entityManager = getEntityManager();

        Query query = entityManager.createNativeQuery("select * from route(?1, ?2);").setParameter(1, from).setParameter(2, to);
        List<Object[]> resultList = query.getResultList();

        entityManager.close();

        return resultList;
    }
}