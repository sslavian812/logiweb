package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.model.TruckModel;
import ru.tsystems.shalamov.services.ServiceLayerException;

import java.util.List;

/**
 * Viewing list of Trucks, adding, editing and deleting Trucks.
 * Viewing list of Trucks, which are suitable for particular order, if:
 * - Truck is intact (not broken).
 * - Truck has sufficient capacity.
 * - Truck is not assigned on any other orders.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface TruckManagementService {

    /**
     * Provides list of all trucks.
     *
     * @return List of {@link ru.tsystems.shalamov.model.TruckModel}
     * @throws ServiceLayerException if some problems with database occur.
     */
    List<TruckModel> findAllTrucks() throws ServiceLayerException;

    /**
     * Created new Truck according to provided information.
     *
     * @param truck {@link ru.tsystems.shalamov.model.TruckModel} aggregating information about truck
     * @throws ServiceLayerException if truck's registration number is already in use.
     */
    void addTruck(TruckModel truck) throws ServiceLayerException;

    /**
     * Updates information about truck.
     *
     * @param truck                 {@link ru.tsystems.shalamov.model.TruckModel} aggregating new information about truck.
     * @param oldRegistrationNumber truck's registration number.
     * @throws ServiceLayerException if
     *                               1) there is no truck with registration number {@code oldRegistrationNumber}
     *                               2) truck with {@code truck.registrationNumber} already exists.
     */
    void updateTruck(TruckModel truck, String oldRegistrationNumber) throws ServiceLayerException;

    /**
     * Removes specified truck by registration number.
     *
     * @param truckRegistrationNumber truck's registration number.
     * @throws ServiceLayerException if:
     *                               1) there is no such truck
     *                               2) given truck is already assigned
     */
    void deleteTruckByRegistrationNumber(String truckRegistrationNumber) throws ServiceLayerException;

    /**
     * Provides information about truck by registration number
     *
     * @param registrationNumber truck's registration number.
     * @return {@link ru.tsystems.shalamov.model.TruckModel}, aggregating
     * information about truck if truck is found, null - otherwise.
     * @throws ServiceLayerException if some problems with database occur.
     */
    TruckModel findTruckModelByRegistrationNumber(String registrationNumber) throws ServiceLayerException;
}
