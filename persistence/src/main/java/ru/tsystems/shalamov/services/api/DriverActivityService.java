package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;

/**
 * For drivers (through ws/rs-interface), recording the actual work time: (PART 2)
 * <p>
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
 * <p>
 * Created by viacheslav on 28.06.2015.
 */
public interface DriverActivityService {

    void beginShift(String personalNumber, DriverStatus driverStatus);

    void changeState(String personalNumber, DriverStatus driverStatus);

    void endShift(String personalNumber);

    void cargoStatusChanged(int cargoId, CargoStatus cargoStatus);
}
