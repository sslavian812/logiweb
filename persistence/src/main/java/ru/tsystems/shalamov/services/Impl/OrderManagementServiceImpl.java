package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServiceLauerException;
import ru.tsystems.shalamov.services.api.OrderManagementService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class OrderManagementServiceImpl implements OrderManagementService {

    DriverDao driverDao;
    OrderDao orderDao;
    TruckDao truckDao;
    DriverStatusDao driverStatusDao;

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public OrderManagementServiceImpl(DriverDao driverDao, OrderDao orderDao,
                                      TruckDao truckDao, DriverStatusDao driverStatusDao, EntityManager em) {
        this.driverDao = driverDao;
        this.orderDao = orderDao;
        this.truckDao = truckDao;
        this.driverStatusDao = driverStatusDao;
        this.em = em;
    }

    @Override
    public void createOrder(OrderEntity order) throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin();
            orderDao.create(order);
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void updateOrder(OrderEntity order) throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin();
            orderDao.update(order);
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public List<OrderEntity> listOrders() throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock;
            List<OrderEntity> list = orderDao.findAll();
            getEntityManager().getTransaction().commit();
            return list;
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public List<TruckEntity> findTrucksForOrder(OrderEntity order) throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin();
            List<TruckEntity> suitableTrucks = truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(order.getTotalweight());
            getEntityManager().getTransaction().commit();
            return suitableTrucks;
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public List<DriverEntity> findDriversForOrder(OrderEntity order) throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin();
            List<DriverEntity> list = driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder();
            getEntityManager().getTransaction().commit();
            return list;
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void assignDriversAndTruckToOrder(List<DriverEntity> drivers, TruckEntity truck, OrderEntity order) throws ServiceLauerException {
        int crewSize = truck.getCrewSize();
        if (drivers.size() < crewSize) {
            throw new ServiceLauerException("not enough drivers provided to assing as crew");
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
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }
}
