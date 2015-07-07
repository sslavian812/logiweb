package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLauerException;

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
public interface DriverAssignmentService {

    DriverAssignment getDriverAssignmentByPersonalNumber(String driverPersonalNumber) throws ServiceLauerException;

    List<String> getCoDriversPersonalNumbers(String driverPersonalNumber) throws ServiceLauerException;

    String getTruckRegistrationNumber(String driverPersonalNumber) throws ServiceLauerException;

    /**
     * Provides Identifier of the order, if the order if complete(
     * cargos added, tuck assigned, truck crew formed).
     *
     * @param driverPersonalNumber
     * @return
     */
    String getOrderIdentifier(String driverPersonalNumber) throws ServiceLauerException;

    List<CargoEntity> getCargoes(String driverPersonalNumber) throws ServiceLauerException;

    List<String> getCargoesDenominations(String driverPersonalNumber) throws ServiceLauerException;
}
