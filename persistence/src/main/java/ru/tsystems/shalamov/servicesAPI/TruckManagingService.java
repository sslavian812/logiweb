package ru.tsystems.shalamov.servicesAPI;

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
public interface TruckManagingService {

    public List<TruckEntity> getAllTrucks();
    public void addTruck(TruckEntity truck);
    public void updateTruck(TruckEntity truck);
    public void deleteTruckById(int TruckId);

    /**
     * Provides list of trucks, meeting following criteria:
     *  1) Truck is intact (not broken).
     *  2) Truck has sufficient capacity.
     *  3) Truck is not assigned on any other orders.
     * @param minimalCapacity minimal car capacity to handle the cargoes in order.
     * @return list of suitable trucks.
     */
    public List<TruckEntity> findAllAvailableTrucks(int minimalCapacity);

}
