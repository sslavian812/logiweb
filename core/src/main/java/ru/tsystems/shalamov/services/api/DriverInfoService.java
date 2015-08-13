package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.services.ServiceLayerException;

import java.util.List;

/**
 * For drivers (through UI-interface) - obtain data on their assignments,
 * by personal number:
 * - Driver's personal number
 * - Co-driver's personal numbers
 * - Truck registration numbers
 * - Order identifier
 * - List of cargoes
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface DriverInfoService {

    /**
     * Provides list of objects, describing assignments.
     *
     * @return List of {@link ru.tsystems.shalamov.model.DriverAssignmentModel}
     * @throws ServiceLayerException if some problems with database occur.
     */
    List<DriverAssignmentModel> findAllDriverAssignments() throws ServiceLayerException;

    /**
     * Provides complete information about driver assignment.
     *
     * @param driverPersonalNumber driver's personal number
     * @return {@link ru.tsystems.shalamov.model.DriverAssignmentModel}
     * if driver is assigned to some orders, null otherwise.
     * @throws ServiceLayerException if
     *                               1) there is no driver with given personal number
     */
    DriverAssignmentModel findDriverAssignmentModelByPersonalNumber(String driverPersonalNumber) throws ServiceLayerException;

    /**
     * Provides information for driver.
     *
     * @param driverPersonalNumber drives's personal number
     * @return {@link ru.tsystems.shalamov.model.DriverAssignmentModel} object,
     * aggregating all information about driver's assignment(if driver is no assigned,
     * then truck registration number and order identifier will be st to NONE, etc)
     * @throws ServiceLayerException if
     *                               1) there is no driver with given personal number
     *                               2) data has inconsistent state (as example: driver is assigned to truck, but there is no order)
     */
    DriverAssignmentModel getPossibleInformationForDriver(String driverPersonalNumber) throws ServiceLayerException;

    /**
     * Provides assignment information according to specifier order
     *
     * @param orderIdentifier order identifier
     * @return {@link ru.tsystems.shalamov.model.DriverAssignmentModel},
     * aggregating information about order and it's assignment.
     * @throws ServiceLayerException
     */
    public DriverAssignmentModel findDriverAssignmentModelByOrderIdentifier(String orderIdentifier) throws ServiceLayerException;
}
