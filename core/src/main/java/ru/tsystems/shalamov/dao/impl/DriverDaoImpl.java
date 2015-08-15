package ru.tsystems.shalamov.dao.impl;

import org.springframework.stereotype.Repository;
import ru.tsystems.shalamov.services.DateUtilities;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.ShiftEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
@Repository
public class DriverDaoImpl extends GenericDaoImpl<DriverEntity> implements DriverDao {

    public DriverDaoImpl(Class<DriverEntity> type) {
        super(type);
    }

    public DriverDaoImpl() {
        super();
    }

    @Override
    public List<DriverEntity> findByMaxWorkingHoursWhereNotAssignedToOrder()
            throws DataAccessLayerException {

        try {
            EntityManager em = getEntityManager();

            TypedQuery<DriverEntity> q = em.createQuery(
                    "SELECT d FROM DriverEntity d JOIN d.driverStatusEntity s " +
                            "WHERE s.status IN :driverStatuses", DriverEntity.class);
            q.setParameter("driverStatuses", Arrays.asList(DriverStatus.UNASSIGNED));

            //todo: test filtering

            // todo: сделать поле в базе и обновлять его при окончании смены. гораздо оптимальней.

            List<DriverEntity> freeDrivers = q.getResultList();
            freeDrivers = freeDrivers.stream()
                    .filter(d -> hastime(d)).collect(Collectors.toList());
            return freeDrivers;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public DriverEntity findByPersonalNumber(String driverPersonalNumber)
            throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<DriverEntity> criteriaQuery = criteriaBuilder.createQuery(DriverEntity.class);
            Root<DriverEntity> driverEntityRoot = criteriaQuery.from(DriverEntity.class);

            return em.createQuery(criteriaQuery.select(driverEntityRoot).where(criteriaBuilder.equal(
                    driverEntityRoot.get("personalNumber"), driverPersonalNumber))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public List<DriverEntity> findByCurrentTruck(int truckId)
            throws DataAccessLayerException {
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

    private boolean hastime(DriverEntity driverEntity) {
        EntityManager em = getEntityManager();

        TypedQuery<ShiftEntity> q = em.createQuery(
                "SELECT s FROM ShiftEntity s " +
                        "WHERE s.driverEntity = :driver AND s.shiftBegin >= :month", ShiftEntity.class);
        q.setParameter("driver", driverEntity);
        q.setParameter("month", DateUtilities.getFirstDayOfMonthDate(new Date()));

        List<ShiftEntity> shifts = q.getResultList();

        return DateUtilities.getWorkingHours(shifts) < DateUtilities.MAX_HOURS;
    }
}
