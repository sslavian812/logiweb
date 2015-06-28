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
    public List<OrderEntity> getOrders();

    public void addOrder(OrderEntity order);

    public OrderStatus getOrderStatusById(int id);

    public CargoStatus getCargoStatusById(int id);

}
