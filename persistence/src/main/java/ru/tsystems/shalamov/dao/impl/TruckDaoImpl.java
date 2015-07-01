package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.EntityManagerUtil;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;

import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.TruckEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 27.06.2015.
 */
public class TruckDaoImpl extends GenericDaoEntityManagerImpl<TruckEntity> implements TruckDao {

    public TruckDaoImpl() {
        super(TruckEntity.class);
    }

    @Override
    public List<TruckEntity> findAll() {
        return EntityManagerUtil.createEntityManager().createQuery("from TruckEntity").getResultList();
    }

}
