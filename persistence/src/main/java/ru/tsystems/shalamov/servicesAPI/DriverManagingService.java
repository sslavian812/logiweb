package ru.tsystems.shalamov.servicesAPI;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.VoidType;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.TruckEntity;

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
public interface DriverManagingService {
    public List<DriverEntity> getAllDrivers();
    public void addDriver(DriverEntity drier);
    public void updateDriver(DriverEntity driver);
    public void deleteDriverById(int driverId);

    /**
     * Provides list of available drivers(less than 176 work hours in the month
     * and not assigned yet for any other Order)
     * @return list of available Drivers.
     */
    public List<DriverEntity> getAvailableDrivers();

    /**
     * Assigns given drivers as a crew for a truck if possible.
     * @param drivers List of drivers. List should have sufficient size
     *                to form crew of the truck. Otherwise it will
     *                be impossible to assign drivers.
     * @param truckId id of truck, which's crew should be formed.
     * @return list of assigned drivers to this truck on success.
     * Otherwise empty list will be returned.
     */
    public List<DriverEntity> assignDriversToTruck(List<DriverEntity> drivers, int truckId);

    /**
     * Automatically assigns drivers to the truck, if there are available.
     * @param truckId id of truck, which's crew should be formed.
     * @return list of assigned drivers to this truck on success.
     * Otherwise empty list will be returned.
     */
    public List<DriverEntity> autoAssignAdriversToTruck(int truckId);
}
