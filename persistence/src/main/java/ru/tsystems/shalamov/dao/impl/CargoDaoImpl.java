package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.api.CargoDao;
import ru.tsystems.shalamov.entities.CargoEntity;

import javax.persistence.EntityManager;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.CargoEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p>
 * Created by viacheslav on 28.06.2015.
 */
public class CargoDaoImpl extends GenericDaoEntityManagerImpl<CargoEntity> implements CargoDao {
    public CargoDaoImpl(EntityManager entityManager) {
        super(CargoEntity.class, entityManager);
    }
}