package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.DriverEntity;
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

    List<DriverEntity> listDrivers() throws ServiceLayerException;

    void addDriver(DriverEntity driver) throws ServiceLayerException;

    void updateDriver(DriverEntity driver) throws ServiceLayerException;

    void deleteDriverByPersonalNumber(String driverPersonalNumber) throws ServiceLayerException;

    boolean checkDriverExistence(String personalNumber) throws ServiceLayerException;
}
