package ru.tsystems.shalamov.dao.impl;

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
    public void create(T newInstance) {
        getEntityManager().persist(newInstance);
    }

    @Override
    public T read(int id) {
        return getEntityManager().find(type, id);
    }

    @Override
    public void update(T transientObject) {
        getEntityManager().refresh(transientObject);
    }

    @Override
    public void delete(T persistentObject) {
        getEntityManager().remove(persistentObject);
    }

    @Override
    public List<T> findAll() {

        EntityManager em = getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> tRoot = query.from(type);
        return em.createQuery(query.select(tRoot)).getResultList();
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}
