package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServieceLauerException;
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
    public void createOrder(OrderEntity order) {
        orderDao.create(order);
    }

    @Override
    public void updateOrder(OrderEntity order) throws ServieceLauerException {
        orderDao.update(order);
    }

    @Override
    public List<OrderEntity> listOrders() {
        return orderDao.findAll();
    }

    @Override
    public List<TruckEntity> findTrucksForOrder(OrderEntity order) {
        List<TruckEntity> suitableTrucks = truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(order.getTotalweight());
        return suitableTrucks;
    }

    @Override
    public List<DriverEntity> findDriversForOrder(OrderEntity order) {
        return driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder();
    }

    @Override
    public void assignDriversAndTruckToOrder(List<DriverEntity> drivers, TruckEntity truck, OrderEntity order) throws ServieceLauerException {
        int crewSize = truck.getCrewSize();
        if (drivers.size() < crewSize) {
            throw new ServieceLauerException("not enough drivers provided to assing as crew");
        }

        order.setTruckEntity(truck);
        orderDao.update(order);

        drivers.subList(0, crewSize);
        for (DriverEntity driver : drivers) {
            DriverStatusEntity driverStatus = driver.getDriverStatusEntity();
            driverStatus.setTruckEntity(truck);
            driverStatusDao.update(driverStatus);
        }
    }
}
