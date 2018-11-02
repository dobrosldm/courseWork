package dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateDao<T, PK extends Serializable> implements DaoInterface<T, PK> {
    private static final SessionFactory ourSessionFactory;
    private Session currentSession;
    private Transaction currentTransaction;
    private Class<T> type;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public HibernateDao(Class <T> type) {
        this.type=type;
    }

    public Session openCurrentSession() {
        currentSession = ourSessionFactory.openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = ourSessionFactory.openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }



    public void create(T entity) {
        openCurrentSessionwithTransaction();
        getCurrentSession().save(entity);
        closeCurrentSessionwithTransaction();
    }

    public void update(T entity) {
        openCurrentSessionwithTransaction();
        getCurrentSession().update(entity);
        closeCurrentSessionwithTransaction();
    }

    public T findById(PK id) {
        openCurrentSession();
        T entity = getCurrentSession().get(type, id);
        closeCurrentSession();
        return entity;
    }

    public void delete(T entity) {
        openCurrentSessionwithTransaction();
        getCurrentSession().delete(entity);
        closeCurrentSessionwithTransaction();
    }

    @SuppressWarnings("unchecked")
    public List<T> selectAll() {
        openCurrentSession();
        List<T> entities = (List<T>) getCurrentSession().createQuery("from "+type.getSimpleName()).list();
        closeCurrentSession();
        return entities;
    }

    /*public void deleteAll() {
        openCurrentSessionwithTransaction();
        List<UserEntity> entityList = findAll();
        for (UserEntity entity : entityList) {
            delete(entity);
        }
        closeCurrentSessionwithTransaction();
    }*/
}