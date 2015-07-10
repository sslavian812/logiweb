package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by viacheslav on 30.06.2015.
 */
public class GenericDaoEntityManagerImpl<T> implements GenericDao<T> {

    private Class<T> type;
    EntityManager em;

    public GenericDaoEntityManagerImpl(Class<T> type, EntityManager entityManager) {
        this.type = type;
        this.em = entityManager;
    }

    @Override
    public void create(T newInstance) throws DataAccessLayerException {
        try {
            getEntityManager().persist(newInstance);
        } catch (Exception exception) {
            throw new DataAccessLayerException(exception);
        }
    }

    @Override
    public T read(int id) throws DataAccessLayerException {
        try {
            return getEntityManager().find(type, id);
        } catch (Exception exception) {
            throw new DataAccessLayerException(exception);
        }
    }

    @Override
    public void update(T transientObject) throws DataAccessLayerException {
//        try {
//            getEntityManager().refresh(transientObject);
//        } catch (Exception exception) {
//            throw new DataAccessLayerException(exception);
//        }
        if (transientObject == null) {
            throw new NullPointerException("Argument must be nt null");
        }

        try {
            getEntityManager().merge(transientObject);
        } catch (Exception e) {
            //todo log
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public void delete(T persistentObject) throws DataAccessLayerException {
        try {
            getEntityManager().remove(persistentObject);
        } catch (Exception exception) {
            throw new DataAccessLayerException(exception);
        }
    }

    @Override
    public List<T> findAll() throws DataAccessLayerException {

        try {
            EntityManager em = getEntityManager();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
            Root<T> tRoot = query.from(type);
            return em.createQuery(query.select(tRoot)).getResultList();
        } catch (Exception exception) {
            throw new DataAccessLayerException(exception);
        }
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}
