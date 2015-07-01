package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.entities.OrderEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.OrderEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class OrderDaoImpl extends GenericDaoEntityManagerImpl<OrderEntity> {

    public OrderDaoImpl() {
        super(OrderEntity.class);
    }
}