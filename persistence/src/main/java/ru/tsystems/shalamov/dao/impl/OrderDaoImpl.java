package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.OrderEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p>
 * Created by viacheslav on 28.06.2015.
 */
public class OrderDaoImpl extends GenericDaoEntityManagerImpl<OrderEntity> implements OrderDao {

    public OrderDaoImpl(EntityManager entityManager) {
        super(OrderEntity.class, entityManager);
    }

    @Override
    public OrderEntity findByTruckId(int truckId) {
        EntityManager em = getEntityManager();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);

        Root orderEntityRoot = criteriaQuery.from(OrderEntity.class);
        Join trucks = orderEntityRoot.join("truckEntity");


        return em.createQuery(criteriaQuery.where(criteriaBuilder.equal(orderEntityRoot.get("status"), OrderStatus.IN_PROGRESS))
                .where(criteriaBuilder.equal(trucks.get("id"), truckId))).getSingleResult();
    }
}