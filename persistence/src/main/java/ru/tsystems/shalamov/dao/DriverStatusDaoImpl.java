package ru.tsystems.shalamov.dao;


import ru.tsystems.shalamov.entities.DriverStatusEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverStatusEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class DriverStatusDaoImpl extends GenericDaoHibernateImpl<DriverStatusEntity, Integer> {
    private DriverStatusDaoImpl(Class<DriverStatusEntity> type) {
        super(type);
    }

    public DriverStatusDaoImpl() {
        this(DriverStatusEntity.class);
    }
}