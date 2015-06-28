package ru.tsystems.shalamov.dao;

import ru.tsystems.shalamov.entities.DriverEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class DriverDaoImpl extends GenericDaoHibernateImpl<DriverEntity, Integer> {
    private DriverDaoImpl(Class<DriverEntity> type) {
        super(type);
    }

    public DriverDaoImpl() {
        this(DriverEntity.class);
    }
}
