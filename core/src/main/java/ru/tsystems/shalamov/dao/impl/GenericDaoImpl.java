package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by viacheslav on 30.06.2015.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    @PersistenceContext
    private EntityManager em;

    private Class<T> type;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    public GenericDaoImpl(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public void setEm(EntityManager em) {
        this.em = em;
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
        try {
            getEntityManager().merge(transientObject);
        } catch (Exception e) {
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
