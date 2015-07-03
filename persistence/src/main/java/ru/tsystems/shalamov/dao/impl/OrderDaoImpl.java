package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.OrderEntity;

import javax.persistence.EntityManager;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.OrderEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class OrderDaoImpl extends GenericDaoEntityManagerImpl<OrderEntity> implements OrderDao {

    public OrderDaoImpl(EntityManager entityManager) {
        super(OrderEntity.class, entityManager);
    }

    @Override
    public OrderEntity findByTruckId(int truckId) {
        //TODO implement
        return null;
    }
}