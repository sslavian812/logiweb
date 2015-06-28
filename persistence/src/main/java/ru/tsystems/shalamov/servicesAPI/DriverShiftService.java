package ru.tsystems.shalamov.servicesAPI;

import ru.tsystems.shalamov.entities.CargoStatus;
import ru.tsystems.shalamov.entities.DriverStatus;

/**
 * For drivers (through ws/rs-interface), recording the actual work time: (PART 2)
 * <p/>
 * Driver begins the working shift:
 * - Driver's personal number;
 * - Status {PRIMARY, AUXILIARY}
 * Driver changes the state:
 * - Personal Driver's number
 * - Status {PRIMARY, AUXILIARY}
 * Driver ends the working shift:
 * - Driver's personal number;
 * Cargo's status changed:
 * - Cargo's id;
 * - Cargo's status {SHIPPED, DELIVERED}
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface DriverShiftService {
    public void beginShift(String personalNumber, DriverStatus driverStatus);
    public void changeState(String personalNumber, DriverStatus driverStatus);
    public void endShift(String personalNumber);
    public void cargoStatusChanged(int cargoId, CargoStatus cargoStatus);
}
