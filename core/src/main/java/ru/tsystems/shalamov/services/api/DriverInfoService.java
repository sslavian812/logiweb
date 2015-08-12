package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.services.DriverAssignment;
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

    List<DriverAssignmentModel> findAllDriverAssignments() throws ServiceLayerException;

    DriverAssignmentModel findDriverAssignmentModelByPersonalNumber(String driverPersonalNumber) throws ServiceLayerException;

    DriverAssignmentModel getPossibleInformationForDriver(String driverPersonalNumber) throws ServiceLayerException;

    public DriverAssignmentModel findDriverAssignmentModelByOrderIdentifier(String orderIdentifier) throws ServiceLayerException;
}
