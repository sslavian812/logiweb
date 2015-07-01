package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.services.DriverAssignment;

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

    DriverAssignment getDriverAssignment(String driverPersonalNumber) throws ServieceLauerException;


    List<String> getCoDriversPersonalNumbers(String driverPersonalNumber);

    String getTruckRegistrationNumber(String driverPersonalNumber);

    /**
     * Provides Identifier of the order, if the order if complete(
     * cargos added, tuck assigned, truck crew formed).
     *
     * @param personalNumber
     * @return
     */
    String getOrderIdentifier(String driverPersonalNumber);

    List<CargoEntity> getCargoes(String driverPersonalNumber);
    List<String> getCargoesDenominations(String driverPersonalNumber);
}
