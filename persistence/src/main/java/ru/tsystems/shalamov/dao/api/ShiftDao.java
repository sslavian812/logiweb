package ru.tsystems.shalamov.dao.api;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.ShiftEntity;

import java.util.Date;

/**
 * Introduces some domain-specific operations for {@link ru.tsystems.shalamov.entities.ShiftEntity}.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface ShiftDao extends GenericDao<ShiftEntity> {

    float getWorkingHoursInMonthByDriver(Date date, DriverEntity driverEntity)
            throws DataAccessLayerException;
}
