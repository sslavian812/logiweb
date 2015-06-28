package ru.tsystems.shalamov.dao;

import ru.tsystems.shalamov.entities.OrderEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.OrderEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class OrderDaoImpl extends GenericDaoHibernateImpl<OrderEntity, Integer> {
    private OrderDaoImpl(Class<OrderEntity> type) {
        super(type);
    }

    public OrderDaoImpl() {
        this(OrderEntity.class);
    }
}