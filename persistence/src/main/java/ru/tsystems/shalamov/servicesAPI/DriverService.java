package ru.tsystems.shalamov.servicesAPI;

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
public interface DriverService {

    List<String> getCoDriversPersonalNumbers(String personalNumber);

    String getTruckRegistrationNumber(String personalNumber);

    /**
     * Provides Identifier of the order, if the order if complete(
     * cargos added, tuck assigned, truck crew formed).
     * @param personalNumber
     * @return
     */
    String getOrderIdentifier(String personalNumber);

    List<String> getCargoesDenominations(String personalNumber);
}
