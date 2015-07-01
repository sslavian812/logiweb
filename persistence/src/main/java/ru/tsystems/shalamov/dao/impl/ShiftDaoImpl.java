package ru.tsystems.shalamov.dao.impl;


import ru.tsystems.shalamov.entities.ShiftEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.ShiftEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class ShiftDaoImpl extends GenericDaoEntityManagerImpl<ShiftEntity> {

    public ShiftDaoImpl() {
        super(ShiftEntity.class);
    }
}