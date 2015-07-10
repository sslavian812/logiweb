package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
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
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<TruckEntity> query = cb.createQuery(TruckEntity.class);
            Root<TruckEntity> truckRoot = query.from(TruckEntity.class);

            Join orders = truckRoot.join("orderEntities");

            return em.createQuery(query.select(truckRoot)
                            .where(cb.equal(truckRoot.get("status"), TruckStatus.INTACT))
                            .where(cb.ge(truckRoot.get("capacity"), minimalCapacity))
                            .where(cb.isNull(orders.get("truck")))
            ).getResultList();
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
        }catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }
}
