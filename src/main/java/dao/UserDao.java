package dao;

import entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserDao extends GenericDao<UserEntity, Integer> {
    public UserDao() {
        super(UserEntity.class);
    }

    public UserEntity findByEmail(String email) {
        return findByAdditionalProperty("email", email);
    }

    public UserEntity findByTelegramId(int id) {
        return findByAdditionalProperty("telegramId", String.valueOf(id));
    }

    private UserEntity findByAdditionalProperty(String property, String value) {
        EntityManager entityManager = getEntityManager();
        CriteriaQuery<UserEntity> criteria = entityManager.getCriteriaBuilder().createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot = criteria.from(UserEntity.class);
        if (property.equals("telegramId"))
            criteria.where(entityManager.getCriteriaBuilder().equal(userEntityRoot.get(property), Integer.valueOf(value)));
        else criteria.where(entityManager.getCriteriaBuilder().equal(userEntityRoot.get(property), value));
        UserEntity entity = entityManager.createQuery(criteria).getSingleResult();
        entityManager.close();
        return entity;
    }
}
