package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.DriverEntity;

import java.util.List;

/**
 * Viewing, adding, deleting and editing drivers.
 * Selecting and assigning drivers on the basis of:
 * - Crew size of the particular Truck.
 * - Time limit 176 hours per month for each driver.
 * - Driver is not assigned now for any other Order.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface DriverManagementService {

    List<DriverEntity> listDrivers();

    void addDriver(DriverEntity driver);

    void updateDriver(DriverEntity driver);

    void deleteDriverById(int driverId);

    /**
     * Provides list of available drivers(less than 176 work hours in the month
     * and not assigned yet for any other Order)
     *
     * @return list of available Drivers.
     */
    List<DriverEntity> findAvailableDrivers();
}
