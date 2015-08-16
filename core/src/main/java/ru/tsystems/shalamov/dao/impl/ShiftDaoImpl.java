package ru.tsystems.shalamov.dao.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.ShiftDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.ShiftEntity;
import ru.tsystems.shalamov.entities.ShiftEntity_;
import ru.tsystems.shalamov.services.DateUtilities;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.ShiftEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
@Repository
public class ShiftDaoImpl extends GenericDaoImpl<ShiftEntity> implements ShiftDao {
    private static final Logger LOG = Logger.getLogger(ShiftDaoImpl.class);


    public ShiftDaoImpl(Class<ShiftEntity> type) {
        super(type);
    }

    public ShiftDaoImpl() {
        super();
    }

    @Override
    public float getWorkingHoursInMonthByDriver(Date date, DriverEntity driverEntity)
            throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<ShiftEntity> criteriaQuery = criteriaBuilder.createQuery(ShiftEntity.class);

            Root shiftEntityRoot = criteriaQuery.from(ShiftEntity.class);

            Join drivers = shiftEntityRoot.join("driver_id");

            List<ShiftEntity> shifts = em.createQuery(criteriaQuery.where(criteriaBuilder.equal(
                            drivers.get("personal_number"), driverEntity.getPersonalNumber()))
                            .where(criteriaBuilder.greaterThan(
                                    shiftEntityRoot.get("shift_begin"), DateUtilities.getFirstDayOfMonthDate(date)))
            ).getResultList();

            float dif = 0;
            for (ShiftEntity s : shifts) {
                if (s.getShiftEnd() != null) {
                    dif += DateUtilities.diffInHours(s.getShiftBegin(), s.getShiftEnd());
                }
            }

            return dif;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }

    @Override
    public ShiftEntity findActiveShiftByDriver(DriverEntity driver) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<ShiftEntity> criteriaQuery = criteriaBuilder.createQuery(ShiftEntity.class);

            Root<ShiftEntity> shiftEntityRoot = criteriaQuery.from(ShiftEntity.class);
            return em.createQuery(criteriaQuery.select(shiftEntityRoot).where(criteriaBuilder.and(
                    criteriaBuilder.equal(shiftEntityRoot.get(ShiftEntity_.driverEntity), driver),
                    criteriaBuilder.isNull(shiftEntityRoot.get(ShiftEntity_.shiftEnd))
            ))).getSingleResult();
        } catch (NoResultException e) {
            LOG.debug(new Exception("no shifts found for driver with personal number [" + driver.getPersonalNumber() + "]. null returned.", e));
            return null;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }
}