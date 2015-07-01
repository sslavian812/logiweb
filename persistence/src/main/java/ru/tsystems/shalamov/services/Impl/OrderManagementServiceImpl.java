package ru.tsystems.shalamov.services.Impl;

import ru.tsystems.shalamov.dao.DaoProvider;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.api.OrderManagementService;
import ru.tsystems.shalamov.services.ServieceLauerException;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class OrderManagementServiceImpl implements OrderManagementService {
    @Override
    public void createOrder(OrderEntity order) {
        DaoProvider.getOrderDao().create(order);
    }

    @Override
    public void updateOrder(OrderEntity order) throws ServieceLauerException {
        DaoProvider.getOrderDao().update(order);
    }

    @Override
    public List<OrderEntity> listOrders() {
        return DaoProvider.getOrderDao().findAll();
    }

    @Override
    public List<TruckEntity> findTrucksForOrder(OrderEntity order) {
        List<TruckEntity> suitableTrucks = DaoProvider.getTruckDao().findAllByCapacity(order.getTotalweight());
        // todo make join here and filter only cars, which are not assigned to Any order.
        return suitableTrucks;
    }

    @Override
    public List<DriverEntity> findDriversForOrder(OrderEntity order) {
        return DaoProvider.getDriverDao().findAvailable();
    }

    @Override
    public void assignDriversAndTruckToOrder(List<DriverEntity> drivers, TruckEntity truck, OrderEntity order) throws ServieceLauerException {
        int crewSize = truck.getCrewSize();
        if (drivers.size() < crewSize) {
            throw new ServieceLauerException("not enough drivers provided to assing as crew");
        }

        order.setTruckEntity(truck);
        DaoProvider.getOrderDao().update(order);

        drivers.subList(0, crewSize);
        DriverStatusDao driverStatusDao = DaoProvider.getDriverStatusDao();
        for (DriverEntity driver : drivers) {
            DriverStatusEntity driverStatus = driver.getDriverStatusEntity();
            driverStatus.setTruckEntity(truck);
            driverStatusDao.update(driverStatus);
        }
    }
}
