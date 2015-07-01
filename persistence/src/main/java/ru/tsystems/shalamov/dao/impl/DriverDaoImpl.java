package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;

import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class DriverDaoImpl extends GenericDaoEntityManagerImpl<DriverEntity> implements DriverDao {

    public DriverDaoImpl() { super(DriverEntity.class);
    }

    @Override
    public List<DriverEntity> findAvailable() {

        // TODO: I'm stuck here for long time, but nothing reached.
//        EntityManager em = EntityManagerUtil.createEntityManager();
//
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//
//        CriteriaQuery<DriverEntity> driverCriteria = cb.createQuery(DriverEntity.class);
//        CriteriaQuery<DriverStatusEntity> statusCriteria = cb.createQuery(DriverStatusEntity.class);
//
//        Root<DriverEntity> driverRoot = driverCriteria.from(DriverEntity.class);
//        Root<DriverStatusEntity> statusRoot = statusCriteria.from(DriverStatusEntity.class);
//
//        Join<DriverEntity, DriverStatusEntity> statusJoin = statusRoot.join("driver_id", JoinType.RIGHT);
//
//        driverCriteria.where(cb.equal(statusJoin.get(DriverStatusEntity_.status), DriverStatus.REST));
//
//        driverCriteria.select(driverRoot);

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
