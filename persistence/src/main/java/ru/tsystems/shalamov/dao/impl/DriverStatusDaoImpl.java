package ru.tsystems.shalamov.dao.impl;


import ru.tsystems.shalamov.entities.DriverStatusEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverStatusEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class DriverStatusDaoImpl extends GenericDaoEntityManagerImpl<DriverStatusEntity> {

    public DriverStatusDaoImpl() {
        super(DriverStatusEntity.class);
    }
}