package ru.tsystems.shalamov.servicesAPI;

import ru.tsystems.shalamov.entities.CargoStatus;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.OrderStatus;

import java.util.List;

/**
 * Viewing list of orders, adding new orders.
 * Viewing state of orders and cargoes.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface PayloadService {
    /**
     * Provides all the orders.
     *
     * @return list of orders.
     */
    List<OrderEntity> getOrders();

    /**
     * Creates new Order.
     *
     * @param order {@link ru.tsystems.shalamov.entities.OrderEntity} to add.
     */
    void addOrder(OrderEntity order);

    /**
     * Provides status of the order.
     *
     * @param orderId id of ther order
     * @return {@link ru.tsystems.shalamov.entities.OrderStatus} enum,
     * indicating status of the order.
     */
    OrderStatus getOrderStatusById(int orderId);

    /**
     * Provides status of the cargo.
     *
     * @param cargoId if of the cargo
     * @return {@link ru.tsystems.shalamov.entities.CargoStatus} enum,
     * indicating status of the cargo.
     */
    CargoStatus getCargoStatusById(int cargoId);

}
