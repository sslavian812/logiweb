package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.model.*;
import ru.tsystems.shalamov.services.ServiceLayerException;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public interface OrderManagementService {

    void createOrderWithCargoes(OrderModel order, List<CargoModel> cargoes) throws ServiceLayerException;


    void updateOrder(OrderModel order, String oldOrderIdentifier) throws ServiceLayerException;

    List<OrderModel> findAllOrders() throws ServiceLayerException;


    void deleteOrderByOrderIdentifierIfNotAssigned(String orderIdentifier) throws ServiceLayerException;

    OrderModel findOrderModelByOrderIdentifier(String orderIdentifier) throws ServiceLayerException;
}
