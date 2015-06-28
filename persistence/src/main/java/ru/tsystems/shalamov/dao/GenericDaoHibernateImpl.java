package ru.tsystems.shalamov.dao;

import org.hibernate.Session;

import java.io.Serializable;

/**
 * Implementation of generic DAO (CRUD) interface.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> type;

    public GenericDaoHibernateImpl(Class<T> type) {
        this.type = type;
    }

    public PK create(T o) {
        return (PK) getSession().save(o);
    }

    public T read(PK id) {
        return (T) getSession().get(type, id);
    }

    public void update(T o) {
        getSession().update(o);
    }

    public void delete(T o) {
        getSession().delete(o);
    }

    private Session getSession() {
        return HibernateUtil.openSession();
    }
}