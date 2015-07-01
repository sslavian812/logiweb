package ru.tsystems.shalamov.dao.api;

import java.util.List;

/**
 * Abstract generic DAO (CRUD) interface.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface GenericDao<T> {

    /**
     * Persist the newInstance object into database
     * // todo: should I return primary key from database here???
     */
    void create(T newInstance);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     */
    T read(int id);

    /**
     * Save changes made to a persistent object.
     */
    void update(T transientObject);

    /**
     * Remove an object from persistent storage in the database
     */
    void delete(T persistentObject);

    /**
     * Provides all the objects;
     * @return
     */
    List<T> findAll();
}