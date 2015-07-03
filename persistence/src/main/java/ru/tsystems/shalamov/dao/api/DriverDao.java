package ru.tsystems.shalamov.dao.api;

import ru.tsystems.shalamov.entities.DriverEntity;

import java.util.List;

/**
 * Introduces some domain-specific operations for {@link ru.tsystems.shalamov.entities.DriverEntity}.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface DriverDao extends GenericDao<DriverEntity> {
    List<DriverEntity> findByMaxWorkingHoursWhereNotAssignedToOrder();

    DriverEntity findByPersonalNumber(String driverPersonalNumber);

    List<DriverEntity> findByCurrentTruck(int truckId);
}
