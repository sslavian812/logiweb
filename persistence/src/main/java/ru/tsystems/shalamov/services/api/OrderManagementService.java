package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;

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
    void createOrder(OrderEntity order);

    /**
     * Updates Order.
     *
     * @param order {@link ru.tsystems.shalamov.entities.OrderEntity} to update.
     *              Should have id, which already presents in the database;
     */
    void updateOrder(OrderEntity order) throws ServieceLauerException;

    /**
     * Provides all the orders.
     *
     * @return list of orders.
     */
    List<OrderEntity> listOrders();


    List<TruckEntity> findTrucksForOrder(OrderEntity order);
        // call TruckManagementService.findAvailableTrucks(order.getTotalWeight());

    List<DriverEntity> findDriversForOrder(OrderEntity order);
        // call DriverManagementService.findAvailableDrivers();  no usage of order for now!!

    /**
     * Assigns given drivers as a crew for a truck and truck to order if possible.
     * @param drivers List of drivers. List should have sufficient size
     *                to form crew of the truck. Otherwise it will
     *                be impossible to assign drivers.
     * @param truck   the truck, which's crew should be formed.
     * @param order   order, which should be performed.
     */
    void assignDriversAndTruckToOrder(List<DriverEntity> drivers, TruckEntity truck, OrderEntity order);





    /**
     * Provides status of the order.
     *
     * @param orderId id of ther order
     * @return {@link ru.tsystems.shalamov.entities.statuses.OrderStatus} enum,
     * indicating status of the order.
     */
    OrderStatus getOrderStatusById(int orderId) throws ServieceLauerException;

    /**
     * Provides status of the cargo.
     *
     * @param cargoId if of the cargo
     * @return {@link ru.tsystems.shalamov.entities.statuses.CargoStatus} enum,
     * indicating status of the cargo.
     */
    CargoStatus getCargoStatusById(int cargoId) throws ServieceLauerException;
}
