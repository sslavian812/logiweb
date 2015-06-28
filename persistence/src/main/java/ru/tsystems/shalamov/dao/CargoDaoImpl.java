package ru.tsystems.shalamov.dao;

import ru.tsystems.shalamov.entities.CargoEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.CargoEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class CargoDaoImpl extends GenericDaoHibernateImpl<CargoEntity, Integer> {
    private CargoDaoImpl(Class<CargoEntity> type) {
        super(type);
    }

    public CargoDaoImpl() {
        this(CargoEntity.class);
    }
}