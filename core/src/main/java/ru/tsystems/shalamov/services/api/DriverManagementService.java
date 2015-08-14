package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.services.ServiceLayerException;

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

    /**
     * Provides list of all drivers.
     *
     * @return List of {@link ru.tsystems.shalamov.model.DriverModel},
     * aggregating information about driver.
     * @throws ServiceLayerException if some database errors occur.
     */
    List<DriverModel> findAllDrivers() throws ServiceLayerException;

    /**
     * Creates new Driver by specified driver model.
     *
     * @param driver {@link ru.tsystems.shalamov.model.DriverModel}
     * @throws ServiceLayerException if personal number is already in use.
     */
    void addDriver(DriverModel driver) throws ServiceLayerException;

    /**
     * Updates existing drivers with personal number {@code oldPersonalNumber},
     * using information from {@link ru.tsystems.shalamov.model.DriverAssignmentModel} {@code driver}
     *
     * @param driver            new information about driver
     * @param oldPersonalNumber driver's to be updated personal number
     * @throws ServiceLayerException if:
     *                               1) there is no such driver
     *                               2) new personal number is already in use
     */
    void updateDriver(DriverModel driver, String oldPersonalNumber) throws ServiceLayerException;

    /**
     * Delete driver with given personal number.
     *
     * @param driverPersonalNumber driver's personal number.
     * @throws ServiceLayerException if:
     *                               1) there is no such driver.
     *                               2) driver has status other than UNASSIGNED.
     */
    void deleteDriverByPersonalNumber(String driverPersonalNumber) throws ServiceLayerException;

    /**
     * Provides information about driver with given personal number.
     *
     * @param personalNumber driver's personal number.
     * @return {@link ru.tsystems.shalamov.model.DriverModel}, aggregating
     * information about driver if found, null - otherwise.
     * @throws ServiceLayerException if some database problems occur.
     */
    DriverModel findDriverModelByPersonalNumber(String personalNumber) throws ServiceLayerException;
}
