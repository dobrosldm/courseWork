package dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class GenericDao<T, PK extends Serializable> implements DaoInterface<T, PK> {
    private static final EntityManagerFactory ourFactory;
    private Class<T> type;

    static {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
        try {
            ourFactory = Persistence.createEntityManagerFactory("ru.seifmo.courseWork.jpa.hibernate");
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public GenericDao(Class <T> type) {
        this.type=type;
    }

    public void finishWork() {
        ourFactory.close();
    }

    private static EntityManager getEntityManager() {
        return ourFactory.createEntityManager();
    }

    public void create(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public T findById(PK id) {
        EntityManager entityManager = getEntityManager();
        T entity = entityManager.find(type, id);
        entityManager.close();
        return entity;
    }

    public void delete(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        //entityManager.remove(entity);
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    public List<T> selectAll() {
        EntityManager entityManager = getEntityManager();
        CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(type);
        criteria.select(criteria.from(type));
        List<T> entities = entityManager.createQuery(criteria).getResultList();
        entityManager.close();
        return entities;
    }

    public void deleteAll() {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        List<T> entityList = selectAll();
        for (T entity : entityList) {
            delete(entity);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}