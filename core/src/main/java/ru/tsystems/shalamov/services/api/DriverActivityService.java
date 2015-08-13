package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;

/**
 * Created by viacheslav on 28.06.2015.
 */
public interface DriverActivityService {

    /**
     * Driver begins his shift. PRIMARY status will be set.
     *
     * @param personalNumber personal number of driver.
     * @throws ServiceLayerException if:
     *                               1) there is no driver with provided personal number
     *                               2) specifier driver is already on the shift.
     */
    void beginShift(String personalNumber) throws ServiceLayerException;

    /**
     * Driver ends his shift. REST status will be set.
     *
     * @param personalNumber driver's personal number.
     * @throws ServiceLayerException if:
     *                               1) there is no driver with provided personal number
     *                               2) specifier drive has no active shift.
     */
    void endShift(String personalNumber) throws ServiceLayerException;

    /**
     * Driver changed his status.
     *
     * @param personalNumber driver's personal number
     * @param driverStatus   driver's new status
     * @throws ServiceLayerException if:
     *                               1) there is no drive with provided personal number
     *                               2) new driver's status is REST (it's not allowed to set REST status directly).
     *                               3) driver's status is REST (it's not allowed to change REST status directly).
     */
    void driverStatusChanged(String personalNumber, DriverStatus driverStatus) throws ServiceLayerException;

    /**
     * Cargo status changing.
     *
     * @param cargoIdentifier cargo identifier
     * @param cargoStatus     new cargo status
     * @throws ServiceLayerException if:
     *                               1) there is no cargo with such cargoIdentifier
     */
    void cargoStatusChanged(String cargoIdentifier, CargoStatus cargoStatus) throws ServiceLayerException;

    /**
     * Complete order. All cargoes associated with given orders should have status DELIVERED.
     * All drivers and truck will be available for new orders.
     *
     * @param orderIdentifier order identifier
     * @throws ServiceLayerException if:
     *                               1) there is no orders with such order identifier
     *                               2) specified order has other than IN_PROGRESS status
     */
    void completeOrder(String orderIdentifier) throws ServiceLayerException;
}
