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
        EntityManager entityManager = getEntityManager();

        CriteriaQuery<UserEntity> criteria = entityManager.getCriteriaBuilder().createQuery(UserEntity.class);
        Root<UserEntity> userEntityRootRoot = criteria.from(UserEntity.class);
        criteria.where(entityManager.getCriteriaBuilder().equal(userEntityRootRoot.get("email"), email));
        UserEntity entity = entityManager.createQuery(criteria).getSingleResult();
        entityManager.close();
        return entity;
    }
}
