package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.EntityManagerUtil;
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

    public GenericDaoEntityManagerImpl(Class<T> type) {
        this.type = type;
    }

    //todo: should I close entityManagers in each method here?

    @Override
    public void create(T newInstance) {
        getManager().persist(newInstance);
    }

    @Override
    public T read(int id) {
        return getManager().find(type, id);
    }

    @Override
    public void update(T transientObject) {
        getManager().refresh(transientObject);
    }

    @Override
    public void delete(T persistentObject) {
        getManager().remove(persistentObject);
    }

    @Override
    public List<T> findAll() {
        //return EntityManagerUtil.createEntityManager().createQuery("from "+ T.class.getSimpleName());

        EntityManager em = EntityManagerUtil.createEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> tRoot = query.from(type);
        return em.createQuery(query.select(tRoot)).getResultList();
    }

    private EntityManager getManager() {
        return EntityManagerUtil.createEntityManager();
    }
}
