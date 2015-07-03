package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class DriverDaoImpl extends GenericDaoEntityManagerImpl<DriverEntity> implements DriverDao {

    public DriverDaoImpl(EntityManager entityManager) { super(DriverEntity.class, entityManager);
    }

    @Override
    public List<DriverEntity> findByMaxWorkingHoursWhereNotAssignedToOrder() {

        EntityManager em = getEntityManager();

        TypedQuery<DriverEntity> q = em.createQuery(
                "SELECT d FROM DriverEntity d JOIN d.driverStatusEntity s WHERE s.status IN :driverStatuses", DriverEntity.class);
        q.setParameter("driverStatuses", Arrays.asList(DriverStatus.PRIMARY, DriverStatus.AUXILIARY));
        q.getResultList();

        return null;
    }

    @Override
    public DriverEntity findByPersonalNumber(String driverPersonalNumber) {
        //TODO implement
        return null;
    }

    @Override
    public List<DriverEntity> findByCurrentTruck(int truckId) {
        // TODO implement
        return null;
    }
}
