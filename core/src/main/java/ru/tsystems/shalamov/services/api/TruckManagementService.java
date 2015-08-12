package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.TruckEntity;
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

    List<TruckModel> findAllTrucks() throws ServiceLayerException;

    void addTruck(TruckModel truck) throws ServiceLayerException;

    void updateTruck(TruckModel truck, String oldRegistrationNumber) throws ServiceLayerException;

    /**
     * Removes Specified truck.
     *
     * @param truckRegistrationNumber Id of the truck to be removed. If there is no truck with
     *                                such Id, the {@link
     *                                ru.tsystems.shalamov.services.ServiceLayerException}
     *                                will be thrown.
     * @hrows LogiwebManagerException if incorrect id provided.
     */
    void deleteTruckByRegistrationNumber(String truckRegistrationNumber) throws ServiceLayerException;

    TruckModel findTruckModelByRegistrationNumber(String registrationNumber) throws ServiceLayerException;
}
