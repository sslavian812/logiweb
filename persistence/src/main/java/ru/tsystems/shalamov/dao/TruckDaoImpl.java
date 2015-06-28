package ru.tsystems.shalamov.dao;

import ru.tsystems.shalamov.entities.TruckEntity;

import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.TruckEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 27.06.2015.
 */
public class TruckDaoImpl extends GenericDaoHibernateImpl<TruckEntity, Integer> implements TruckDao {
    private TruckDaoImpl(Class<TruckEntity> type) {
        super(type);
    }

    public TruckDaoImpl() {
        this(TruckEntity.class);
    }

    @Override
    public List<TruckEntity> getAll() {
        return null;
        //todo STUB
    }

}
