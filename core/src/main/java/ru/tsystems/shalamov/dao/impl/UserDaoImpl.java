package ru.tsystems.shalamov.dao.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.UserDao;
import ru.tsystems.shalamov.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by viacheslav on 24.07.2015.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CargoDaoImpl.class);

    @Override
    public User findByusername(String username) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);

            return em.createQuery(criteriaQuery.select(userRoot).where(criteriaBuilder.equal(
                    userRoot.get("username"), username))).getSingleResult();
        } catch (NoResultException e) {
            LOG.warn(new Exception("no users found with username [" + username + "]. null returned.", e));
            return null;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }
}
