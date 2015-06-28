package ru.tsystems.shalamov.dao;


import ru.tsystems.shalamov.entities.ShiftEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.ShiftEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class ShiftDaoImpl extends GenericDaoHibernateImpl<ShiftEntity, Integer> {
    private ShiftDaoImpl(Class<ShiftEntity> type) {
        super(type);
    }

    public ShiftDaoImpl() {
        this(ShiftEntity.class);
    }
}