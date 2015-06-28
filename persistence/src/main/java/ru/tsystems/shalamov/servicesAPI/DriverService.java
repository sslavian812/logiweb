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
    public List<String> getCoDriversPersonalNumbers(String personalNumber);

    public String getTruckRegistrationNumber(String personalNumber);

    public String getOrderIdentifier(String personalNumber);

    public List<String> getCargoesDenominations(String personalNumber);
}
