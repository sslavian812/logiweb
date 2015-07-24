package ru.tsystems.shalamov.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.OrderEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
@Repository
public class OrderDaoImpl extends GenericDaoImpl<OrderEntity> implements OrderDao {


    public OrderDaoImpl(Class<OrderEntity> type) {
        super(type);
    }

    public OrderDaoImpl()
    {super();}

    @Override
    public OrderEntity findByTruckId(int truckId) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);

            Root orderEntityRoot = criteriaQuery.from(OrderEntity.class);
            Join trucks = orderEntityRoot.join("truckEntity");


            return em.createQuery(criteriaQuery.where(criteriaBuilder.equal(orderEntityRoot.get("status"), OrderStatus.IN_PROGRESS))
                    .where(criteriaBuilder.equal(trucks.get("id"), truckId))).getSingleResult();
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public OrderEntity findByOrderIdentifier(String orderIdentifier) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);

            Root<OrderEntity> orderEntityRoot = criteriaQuery.from(OrderEntity.class);
            return em.createQuery(criteriaQuery.select(orderEntityRoot).where(criteriaBuilder.equal(
                    orderEntityRoot.get("orderIdentifier"), orderIdentifier))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }
}