package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.OrderManagementService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class OrderManagementServiceImpl implements OrderManagementService {

    DriverDao driverDao;
    TruckDao truckDao;
    DriverStatusDao driverStatusDao;
    OrderDao orderDao;
    CargoDao cargoDao;

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
        } catch (DataAccessLayerException e) {
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
            orderDao.create(order);
            for (CargoEntity e : cargoes)
                cargoDao.create(e);

            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
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
        } catch (DataAccessLayerException e) {
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
        } catch (DataAccessLayerException e) {
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
            throw new ServiceLayerException("not enough drivers provided to assing as crew");
        }

        try {
            getEntityManager().getTransaction().begin();

            order.setTruckEntity(truck);
            orderDao.update(order);
            getEntityManager().refresh(order);

            drivers.subList(0, crewSize);
            for (DriverEntity driver : drivers) {
                DriverStatusEntity driverStatus = driver.getDriverStatusEntity();
                driverStatus.setTruckEntity(truck);
                driverStatusDao.update(driverStatus);
            }
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void deleteOrderByOrderIdentifierIfNotAssigned(String orderIdentifier) throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock;
            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);
            if (order == null) {
                // todo log;
                throw new ServiceLayerException("There are no orders with" +
                        " order identifier [" + orderIdentifier + "]");
            }
            if (order.getTruckEntity() == null)
                orderDao.delete(order);
            else {
                throw new ServiceLayerException("cant delete order " +
                        order.getOrderIdentifier() + ". There is a truck assigned to it.");
            }
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }
}
