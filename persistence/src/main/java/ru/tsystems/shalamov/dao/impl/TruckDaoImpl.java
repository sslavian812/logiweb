package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity_;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.TruckEntity_;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.TruckEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 27.06.2015.
 */
public class TruckDaoImpl extends GenericDaoEntityManagerImpl<TruckEntity> implements TruckDao {

    public TruckDaoImpl(EntityManager entityManager) {
        super(TruckEntity.class, entityManager);
    }

    @Override
    public List<TruckEntity> findAll() throws DataAccessLayerException {
        try {
            return getEntityManager().createQuery("from TruckEntity").getResultList();
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public List<TruckEntity> findByMinCapacityWhereStatusOkAndNotAssignedToOrder(int minimalCapacity)
            throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();
            TypedQuery<TruckEntity> q = em.createQuery(
                    "SELECT t FROM TruckEntity t "
                    +"LEFT OUTER JOIN t.driverStatusEntities s" +
                            " WHERE s.id IS null"
                            +" AND t.status=:status AND t.capacity >= :minCapacity"
                            , TruckEntity.class);
            q.setParameter("status", TruckStatus.INTACT);
            q.setParameter("minCapacity", minimalCapacity);
            return q.getResultList();

        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public TruckEntity findByRegistrationNumber(String truckRegistrationNumber) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<TruckEntity> criteriaQuery = criteriaBuilder.createQuery(TruckEntity.class);

            Root<TruckEntity> truckEntityRoot = criteriaQuery.from(TruckEntity.class);
            return em.createQuery(criteriaQuery.select(truckEntityRoot).where(criteriaBuilder.equal(
                    truckEntityRoot.get("registrationNumber"), truckRegistrationNumber))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }
}
