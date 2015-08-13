package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
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
     * @return List of {@link ru.tsystems.shalamov.model.DriverModel},
     * aggregating information about driver.
     * @throws ServiceLayerException if some database errors occur.
     */
    List<DriverModel> findAllDrivers() throws ServiceLayerException;

    /**
     * Creates new Driver by specified driver model.
     * @param driver {@link ru.tsystems.shalamov.model.DriverModel}
     * @throws ServiceLayerException if data is inconsistent.
     */
    void addDriver(DriverModel driver) throws ServiceLayerException;

    void updateDriver(DriverModel driver, String oldPersonalNumber) throws ServiceLayerException;

    void deleteDriverByPersonalNumber(String driverPersonalNumber) throws ServiceLayerException;

//    boolean checkDriverExists(String personalNumber) throws ServiceLayerException;


    DriverModel findDriverModelByPersonalNumber(String personalNumber) throws ServiceLayerException;
}
