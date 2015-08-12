package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.model.*;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.Util;
import ru.tsystems.shalamov.services.api.DriverManagementService;
import ru.tsystems.shalamov.services.api.OrderManagementService;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 01.07.2015.
 */
@Service
public class OrderManagementServiceImpl implements OrderManagementService {

    @Autowired
    public OrderManagementServiceImpl(OrderDao orderDao,
                                      CargoDao cargoDao) {
        this.orderDao = orderDao;
        this.cargoDao = cargoDao;
    }

    private OrderDao orderDao;
    private CargoDao cargoDao;

    private static final Logger LOG = Logger.getLogger(OrderManagementServiceImpl.class);






    @Override
    @Transactional
    public void createOrderWithCargoes(OrderModel order, List<CargoModel> cargoes) throws ServiceLayerException {
        try {
            if (orderDao.findByOrderIdentifier(order.getOrderIdentifier()) != null) {
                throw new ServiceLayerException("Order Identifier already in use");
            }
            OrderEntity orderEntity = new OrderEntity(order.getOrderIdentifier());
            List<CargoEntity> cargoEntities = cargoes.stream()
                    .map(c -> new CargoEntity(
                            c.getDenomination(),
                            c.getWeight(),
                            c.getStatus(),
                            orderEntity,
                            Util.generateCargoIdentifier(
                                    order.getOrderIdentifier(),
                                    c.getDenomination(),
                                    c.getWeight())
                    )).collect(Collectors.toList());
            cargoEntities.forEach(c -> c.setOrderEntity(orderEntity));
            orderEntity.setCargoEntities(cargoEntities);
            orderDao.create(orderEntity);

            for (CargoEntity e : cargoEntities)
                cargoDao.create(e);

            LOG.info("Order created. Order Identifier: " + order.getOrderIdentifier());

            for (CargoEntity e : cargoEntities)
                LOG.info("Cargo for order [" + order.getOrderIdentifier()
                        + "] created: [" + e.getDenomination() + "]");

        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void updateOrder(OrderModel order, String oldOrderIdentifier) throws ServiceLayerException {

        OrderEntity orderEntity = findOrderByOrderIdentifier(oldOrderIdentifier);

        if (orderEntity == null) {
            LOG.warn("Failed update of order + [" + oldOrderIdentifier + "]. Order does not exists.");
            throw new ServiceLayerException("No such order.");
        }

        if (!oldOrderIdentifier.equals(order.getOrderIdentifier())) {
            if (findOrderByOrderIdentifier(order.getOrderIdentifier()) != null) {
                LOG.warn("Fail to change order identifier. [" + order.getOrderIdentifier() + "] already exists.");
                throw new ServiceLayerException("Fail to update order. "
                        + "Order with new personal number already exists");
            }
        }

        orderEntity.setOrderIdentifier(order.getOrderIdentifier());

        try {
            orderDao.update(orderEntity);

            LOG.info("Order updated. [" + order.getOrderIdentifier() + "] ");
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public List<OrderModel> findAllOrders() throws ServiceLayerException {
        try {
            return orderDao.findAll().stream()
                    .map(o -> new OrderModel(o))
                    .collect(Collectors.toList());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void deleteOrderByOrderIdentifierIfNotAssigned(String orderIdentifier)
            throws ServiceLayerException {
        try {
            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);
            if (order == null) {
                throw new ServiceLayerException("There are no orders with" +
                        " order identifier: [" + orderIdentifier + "]");
            }
            if (order.getStatus() == OrderStatus.UNASSIGNED)
                orderDao.delete(order);
            else {
                throw new ServiceLayerException("cant delete order " +
                        order.getOrderIdentifier() + ". There is a truck assigned to it.");
            }
            LOG.info("Order deleted. Order Identifier: " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public OrderModel findOrderModelByOrderIdentifier(String orderIdentifier) throws ServiceLayerException {
        try {
            return new OrderModel(orderDao.findByOrderIdentifier(orderIdentifier));
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }





    @Transactional
    private OrderEntity findOrderByOrderIdentifier(String orderIdentifier)
            throws ServiceLayerException {
        try {
            return orderDao.findByOrderIdentifier(orderIdentifier);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }




    @Transactional
    private void createOrder(OrderEntity order) throws ServiceLayerException {
        try {
            if (orderDao.findByOrderIdentifier(order.getOrderIdentifier()) != null) {
                throw new ServiceLayerException("Order Identifier already in use");
            }
            orderDao.create(order);
            LOG.info("Order created. Order Identifier: " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }


    @Transactional
    private void createOrderWithCargoes(OrderEntity order, List<CargoEntity> cargoes)
            throws ServiceLayerException {
        try {
            if (orderDao.findByOrderIdentifier(order.getOrderIdentifier()) != null) {
                throw new ServiceLayerException("Order Identifier already in use");
            }
            order.setCargoEntities(cargoes);
            orderDao.create(order);

            for (CargoEntity e : cargoes)
                cargoDao.create(e);

            LOG.info("Order created. Order Identifier: " + order.getOrderIdentifier());
            for (CargoEntity e : cargoes)
                LOG.info("Cargo for order [" + order.getOrderIdentifier()
                        + "] created: [" + e.getDenomination() + "]");

        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Transactional
    private void updateOrder(OrderEntity order) throws ServiceLayerException {
        try {
            orderDao.update(order);
            LOG.info("Order updated. Order Identifier(may be new): " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Transactional
    private List<OrderEntity> listOrders() throws ServiceLayerException {
        try {
            return orderDao.findAll();
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }
}