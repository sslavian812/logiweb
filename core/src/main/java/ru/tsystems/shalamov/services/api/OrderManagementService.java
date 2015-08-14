package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.model.CargoModel;
import ru.tsystems.shalamov.model.OrderModel;
import ru.tsystems.shalamov.services.ServiceLayerException;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public interface OrderManagementService {

    /**
     * Created new Orders and associates cargoes to it.
     *
     * @param order   Orders information.
     * @param cargoes cargo Information.
     * @throws ServiceLayerException if order with suck identifier already exists.
     */
    void createOrderWithCargoes(OrderModel order, List<CargoModel> cargoes) throws ServiceLayerException;

    /**
     * Adds a cargo to specified order.
     *
     * @param orderIdentifier order's identifier
     * @param cargo           information about cargo
     * @throws ServiceLayerException if
     *                               1) there is no such order
     *                               2) order has status other than UNASSIGNED
     */
    void addCargoToOrder(String orderIdentifier, CargoModel cargo) throws ServiceLayerException;

    /**
     * Deletes cargo by identifier and removes it from orders it was associated before.
     *
     * @param cargoIdentifier cargo's identifier
     * @throws ServiceLayerException if:
     *                               1) there is no such cargo
     *                               2) order containing given cargo has status other than UNASSIGNED.
     */
    void deleteCargo(String cargoIdentifier) throws ServiceLayerException;

    /**
     * Updates information about order.
     *
     * @param order              {@link ru.tsystems.shalamov.model.OrderModel} containing information about orderd.
     * @param oldOrderIdentifier orders's to be updated identifier.
     * @throws ServiceLayerException if:
     *                               1) there if no such order {@code oldOrderIdentifier}
     *                               2) order has status other than UNASSIGNED
     */
    void updateOrder(OrderModel order, String oldOrderIdentifier) throws ServiceLayerException;

    /**
     * Provides list of all orders.
     *
     * @return List of {@link ru.tsystems.shalamov.model.OrderModel}
     * @throws ServiceLayerException if some database problems occur
     */
    List<OrderModel> findAllOrders() throws ServiceLayerException;

    /**
     * Deletes specifier order if possible.
     *
     * @param orderIdentifier order's identifier.
     * @throws ServiceLayerException if:
     *                               1) there is no such order
     *                               2) order has status other than UNASSIGNED
     */
    void deleteOrderByOrderIdentifierIfNotAssigned(String orderIdentifier) throws ServiceLayerException;

    /**
     * Provides order by identifier.
     *
     * @param orderIdentifier order's identifier.
     * @return {@link ru.tsystems.shalamov.model.OrderModel} if orders exists, null - otherwise.
     * @throws ServiceLayerException if some problems in database occur.
     */
    OrderModel findOrderModelByOrderIdentifier(String orderIdentifier) throws ServiceLayerException;
}
