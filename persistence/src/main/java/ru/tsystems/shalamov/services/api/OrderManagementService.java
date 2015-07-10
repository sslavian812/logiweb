package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServiceLayerException;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public interface OrderManagementService {

    /**
     * Creates new Order.
     *
     * @param order {@link ru.tsystems.shalamov.entities.OrderEntity} to add.
     */
    void createOrder(OrderEntity order) throws ServiceLayerException;

    void createOrderWithCargoes(OrderEntity order, List<CargoEntity> cargoes) throws ServiceLayerException;

    /**
     * Updates Order.
     *
     * @param order {@link ru.tsystems.shalamov.entities.OrderEntity} to update.
     *              Should have id, which already presents in the database;
     */
    void updateOrder(OrderEntity order) throws ServiceLayerException;

    /**
     * Provides all the orders.
     *
     * @return list of orders.
     */
    List<OrderEntity> listOrders() throws ServiceLayerException;


    /**
     * Provides list of trucks, meeting following criteria:
     * 1) Truck is intact (not broken).
     * 2) Truck has sufficient capacity.
     * 3) Truck is not assigned on any other orders.
     *
     * @param order order which for suitable truck will be searched.
     * @return list of suitable trucks.
     */
    List<TruckEntity> findTrucksForOrder(OrderEntity order) throws ServiceLayerException;

    /**
     * Provides list of available drivers(less than 176 work hours in the month
     * and not assigned yet for any other Order)
     *
     * @param order not userd for now
     * @return list of available Drivers.
     */
    List<DriverEntity> findDriversForOrder(OrderEntity order) throws ServiceLayerException;

    /**
     * Assigns given drivers as a crew for a truck and truck to order if possible.
     *
     * @param drivers List of drivers. List should have sufficient size
     *                to form crew of the truck. Otherwise it will
     *                be impossible to assign drivers.
     * @param truck   the truck, which's crew should be formed.
     * @param order   order, which should be performed.
     */
    void assignDriversAndTruckToOrder(List<DriverEntity> drivers,
                                      TruckEntity truck, OrderEntity order)
            throws ServiceLayerException;


//    /**
//     * Provides status of the order.
//     *
//     * @param orderId id of ther order
//     * @return {@link ru.tsystems.shalamov.entities.statuses.OrderStatus} enum,
//     * indicating status of the order.
//     */
//    OrderStatus getOrderStatusById(int orderId) throws ServieceLauerException;
//
//    /**
//     * Provides status of the cargo.
//     *
//     * @param cargoId if of the cargo
//     * @return {@link ru.tsystems.shalamov.entities.statuses.CargoStatus} enum,
//     * indicating status of the cargo.
//     */
//    CargoStatus getCargoStatusById(int cargoId) throws ServieceLauerException;
}
