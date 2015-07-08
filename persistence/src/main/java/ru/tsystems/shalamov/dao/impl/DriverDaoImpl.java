package ru.tsystems.shalamov.dao.impl;

import org.apache.log4j.Logger;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class DriverDaoImpl extends GenericDaoEntityManagerImpl<DriverEntity> implements DriverDao {

    private static Logger logger = Logger.getLogger(DriverDaoImpl.class);

    public DriverDaoImpl(EntityManager entityManager) {
        super(DriverEntity.class, entityManager);
    }

    @Override
    public List<DriverEntity> findByMaxWorkingHoursWhereNotAssignedToOrder() throws DataAccessLayerException {

        try {
            EntityManager em = getEntityManager();

            TypedQuery<DriverEntity> q = em.createQuery(
                    "SELECT d FROM DriverEntity d JOIN d.driverStatusEntity s WHERE s.status IN :driverStatuses", DriverEntity.class);
            q.setParameter("driverStatuses", Arrays.asList(DriverStatus.PRIMARY, DriverStatus.AUXILIARY));

            //todo: by working hours.

            return q.getResultList();
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public DriverEntity findByPersonalNumber(String driverPersonalNumber) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<DriverEntity> criteriaQuery = criteriaBuilder.createQuery(DriverEntity.class);
            Root<DriverEntity> driverEntityRoot = criteriaQuery.from(DriverEntity.class);

            return em.createQuery(criteriaQuery.select(driverEntityRoot).where(criteriaBuilder.equal(
                    driverEntityRoot.get("personal_number"), driverPersonalNumber))).getSingleResult();
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public List<DriverEntity> findByCurrentTruck(int truckId) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<DriverEntity> criteriaQuery = criteriaBuilder.createQuery(DriverEntity.class);

            Root driverEntityRoot = criteriaQuery.from(DriverEntity.class);
            Join statuses = driverEntityRoot.join("driverStatusEntity");
            Join trucks = statuses.join("truckEntity");

            return em.createQuery(criteriaQuery.where(
                    criteriaBuilder.equal(trucks.get("id"), truckId))).getResultList();
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }
}
