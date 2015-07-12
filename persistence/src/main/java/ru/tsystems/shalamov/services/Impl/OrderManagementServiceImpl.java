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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class OrderManagementServiceImpl implements OrderManagementService {

    private DriverDao driverDao;
    private TruckDao truckDao;
    private DriverStatusDao driverStatusDao;
    private OrderDao orderDao;
    private CargoDao cargoDao;

    private static final Logger LOG = Logger.getLogger(OrderManagementServiceImpl.class);

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public OrderManagementServiceImpl(DriverDao driverDao, OrderDao orderDao,
                                      TruckDao truckDao, DriverStatusDao driverStatusDao,
                                      CargoDao cargoDao, EntityManager em) {

        this.driverDao = driverDao;
        this.orderDao = orderDao;
        this.truckDao = truckDao;
        this.driverStatusDao = driverStatusDao;
        this.cargoDao = cargoDao;
        this.em = em;
    }

    @Override
    public void createOrder(OrderEntity order) throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            if (orderDao.findByOrderIdentifier(order.getOrderIdentifier()) != null) {
                throw new ServiceLayerException("Order Identifier already in use");
            }
            orderDao.create(order);
            getEntityManager().getTransaction().commit();
            LOG.info("Order created. Order Identifier: " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void createOrderWithCargoes(OrderEntity order, List<CargoEntity> cargoes)
            throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            if (orderDao.findByOrderIdentifier(order.getOrderIdentifier()) != null) {
                throw new ServiceLayerException("Order Identifier already in use");
            }
            order.setCargoEntities(cargoes);

            orderDao.create(order);

            for (CargoEntity e : cargoes)
                cargoDao.create(e);


            getEntityManager().getTransaction().commit();

            LOG.info("Order created. Order Identifier: " + order.getOrderIdentifier());
            for (CargoEntity e : cargoes)
                LOG.info("Cargo for order [" + order.getOrderIdentifier()
                        + "] created: [" + e.getDenomination() + "]");

        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void updateOrder(OrderEntity order) throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            orderDao.update(order);
            getEntityManager().getTransaction().commit();

            LOG.info("Order updated. Order Identifier(may be new): " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public List<OrderEntity> listOrders() throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock;
            List<OrderEntity> list = orderDao.findAll();
            getEntityManager().getTransaction().commit();
            return list;
            //todo is a transaction required here?
            //return orderDao.findAll();
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public List<TruckEntity> findTrucksForOrder(OrderEntity order)
            throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            List<TruckEntity> suitableTrucks = truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(order.getTotalweight());
            getEntityManager().getTransaction().commit();
            return suitableTrucks;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public List<DriverEntity> findDriversForOrder(OrderEntity order)
            throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            List<DriverEntity> list = driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder();
            getEntityManager().getTransaction().commit();
            return list;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void assignDriversAndTruckToOrder(List<DriverEntity> drivers,
                                             TruckEntity truck, OrderEntity order)
            throws ServiceLayerException {

        int crewSize = truck.getCrewSize();
        if (drivers.size() < crewSize) {
            throw new ServiceLayerException("not enough drivers provided to assign as crew");
        }

        try {
            getEntityManager().getTransaction().begin();

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

            getEntityManager().getTransaction().commit();

            String assignedDrivers = drivers.stream()
                    .map(d -> d.getPersonalNumber())
                    .reduce((a, b) -> a + ", " + b).get();

            LOG.info("Assignment: order:[" + order.getOrderIdentifier()
                    + "] trucks:[" + truck.getRegistrationNumber()
                    + "] drivers:{" + assignedDrivers + "}");
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void deleteOrderByOrderIdentifierIfNotAssigned(String orderIdentifier)
            throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock;
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
            getEntityManager().getTransaction().commit();
            LOG.info("Order deleted. Order Identifier: " + order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
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
