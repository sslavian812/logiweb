package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.TruckEntity;

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
     * Provides all the trucks.
     *
     * @return list of trucks.
     */
    List<TruckEntity> getAllTrucks();

    /**
     * Adds a new truck.
     *
     * @param truck {@link ru.tsystems.shalamov.entities.TruckEntity} instance,
     *              describing new truck. The id will be generated
     *              Automatically.
     */
    void addTruck(TruckEntity truck);

    /**
     * Updates information about the tuck.
     *
     * @param truck {@link ru.tsystems.shalamov.entities.TruckEntity} instance,
     *              describing the truck. There should be one in the database
     *              with the same id. Otherwise, the {@link
     *              ServieceLauerException}
     *              will be thrown.
     * @throws ServieceLauerException if incorrect id provided.
     */
    void updateTruck(TruckEntity truck) throws ServieceLauerException;

    /**
     * Removes Specified truck.
     *
     * @param truckId Id of the truck to be removed. If there is no truck with
     *                such Id, the {@link
     *                ServieceLauerException}
     *                will be thrown.
     * @hrows LogiwebManagerException if incorrect id provided.
     */
    void deleteTruckById(int truckId) throws ServieceLauerException;

    /**
     * Provides list of trucks, meeting following criteria:
     * 1) Truck is intact (not broken).
     * 2) Truck has sufficient capacity.
     * 3) Truck is not assigned on any other orders.
     *
     * @param minimalCapacity minimal capacity to handle the cargoes in order.
     * @return list of suitable trucks.
     */
    List<TruckEntity> findAvailableTrucks(int minimalCapacity);

}
