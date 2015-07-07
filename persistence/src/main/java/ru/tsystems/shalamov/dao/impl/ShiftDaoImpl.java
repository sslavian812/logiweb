package ru.tsystems.shalamov.dao.impl;


import ru.tsystems.shalamov.DateUtilities;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.ShiftDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.ShiftEntity;

import javax.persistence.EntityManager;
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
public class ShiftDaoImpl extends GenericDaoEntityManagerImpl<ShiftEntity> implements ShiftDao {

    public ShiftDaoImpl(EntityManager entityManager) {
        super(ShiftEntity.class, entityManager);
    }

    @Override
    public float GetWorkingHoursInMonthByDriver(Date date, DriverEntity driverEntity)
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
                if (s.getShiftEnd() != null)
                    dif += DateUtilities.diffInHours(s.getShiftBegin(), s.getShiftEnd());
            }

            return dif;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }
}