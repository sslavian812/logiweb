package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.OrderManagementService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by viacheslav on 01.07.2015.
 */
@Service
public class OrderManagementServiceImpl implements OrderManagementService {

    @Autowired
    public OrderManagementServiceImpl(DriverDao driverDao, OrderDao orderDao,
                                      TruckDao truckDao, DriverStatusDao driverStatusDao,
                                      CargoDao cargoDao) {
        this.driverDao = driverDao;
        this.orderDao = orderDao;
        this.truckDao = truckDao;
        this.driverStatusDao = driverStatusDao;
        this.cargoDao = cargoDao;
    }

    private DriverDao driverDao;
    private TruckDao truckDao;
    private DriverStatusDao driverStatusDao;
    private OrderDao orderDao;
    private CargoDao cargoDao;

    private static final Logger LOG = Logger.getLogger(OrderManagementServiceImpl.class);


    @Override
    @Transactional
    public void createOrder(OrderEntity order) throws ServiceLayerException {
        try {
            if (orderDao.findByOrderIdentifier(order.getOrderIdentifier()) != null) {
                throw new ServiceLayerException("Order Identifier already in use");
            }
            orderDao.create(order);
            LOG.info("Order created. Order Identifier: " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void createOrderWithCargoes(OrderEntity order, List<CargoEntity> cargoes)
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
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void updateOrder(OrderEntity order) throws ServiceLayerException {
        try {
            orderDao.update(order);
            LOG.info("Order updated. Order Identifier(may be new): " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public List<OrderEntity> listOrders() throws ServiceLayerException {
        try {
            return orderDao.findAll();
            //todo is a transaction necessary here?
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public List<TruckEntity> findTrucksForOrder(OrderEntity order)
            throws ServiceLayerException {
        try {
            return truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(order.getTotalweight());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public List<DriverEntity> findDriversForOrder(OrderEntity order)
            throws ServiceLayerException {
        try {
            return driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder();
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void assignDriversAndTruckToOrder(List<DriverEntity> drivers,
                                             TruckEntity truck, OrderEntity order)
            throws ServiceLayerException {

        // TODO: filter incoming drivers by status "REST".
        // TODO: filter trucks if unassigned
        int crewSize = truck.getCrewSize();
        if (drivers.size() < crewSize) {
            throw new ServiceLayerException("not enough drivers provided to assign as crew");
        }

        try {
            order.setTruckEntity(truck);
            order.setStatus(OrderStatus.IN_PROGRESS);
            orderDao.update(order);

            drivers.subList(0, crewSize);

            for (Iterator<DriverEntity> it = drivers.iterator(); it.hasNext(); ) {
                DriverStatusEntity driverStatus = it.next().getDriverStatusEntity();
                driverStatus.setTruckEntity(truck);
                if (!it.hasNext()) {
                    driverStatus.setStatus(DriverStatus.PRIMARY);
                } else {
                    driverStatus.setStatus(DriverStatus.AUXILIARY);
                }
                driverStatusDao.update(driverStatus);
            }

            String assignedDrivers = drivers.stream()
                    .map(d -> d.getPersonalNumber())
                    .reduce((a, b) -> a + ", " + b).get();

            LOG.info("Assignment: order:[" + order.getOrderIdentifier()
                    + "] trucks:[" + truck.getRegistrationNumber()
                    + "] drivers:{" + assignedDrivers + "}");
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
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
            if (order.getTruckEntity() == null)
                orderDao.delete(order);
            else {
                throw new ServiceLayerException("cant delete order " +
                        order.getOrderIdentifier() + ". There is a truck assigned to it.");
            }
            LOG.info("Order deleted. Order Identifier: " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public OrderEntity findOrderByOrderIdentifier(String orderIdentifier)
            throws ServiceLayerException {
        try {
            return orderDao.findByOrderIdentifier(orderIdentifier);
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }
}
