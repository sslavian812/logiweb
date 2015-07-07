package ru.tsystems.shalamov.dao.api;

import ru.tsystems.shalamov.dao.DataAccessLayerException;

import java.util.List;

/**
 * Abstract generic DAO (CRUD) interface.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface GenericDao<T> {

    /**
     * Persist the newInstance object into database
     */
    void create(T newInstance) throws DataAccessLayerException;

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     */
    T read(int id) throws DataAccessLayerException;

    /**
     * Save changes made to a persistent object.
     */
    void update(T transientObject) throws DataAccessLayerException;

    /**
     * Remove an object from persistent storage in the database
     */
    void delete(T persistentObject) throws DataAccessLayerException;

    /**
     * Provides all the objects;
     *
     * @return
     */
    List<T> findAll() throws DataAccessLayerException;
}