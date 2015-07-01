package ru.tsystems.shalamov.dao;

import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.dao.impl.*;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DaoFactory {

    private static DriverDao driverDao = null;
    private static OrderDao orderDao = null;
    private static TruckDao truckDao = null;
    private static DriverStatusDao driverStatusDao = null;


    private static CargoDao cargoDao = null;
    private static ShiftDao shiftDao = null;

    public static CargoDao getCargoDao() {
        if (cargoDao == null) {
            cargoDao = new CargoDaoImpl();
        }
        return cargoDao;
    }

    public static ShiftDao getShiftDao() {
        if (shiftDao == null) {
            shiftDao = new ShiftDaoImpl();
        }
        return shiftDao;
    }


    public static DriverDao getDriverDao() {
        if (driverDao == null) {
            driverDao = new DriverDaoImpl();
        }
        return driverDao;
    }

    public static OrderDao getOrderDao() {
        if (orderDao == null) {
            orderDao = new OrderDaoImpl();
        }
        return orderDao;
    }

    public static TruckDao getTruckDao() {
        if (truckDao == null) {
            truckDao = new TruckDaoImpl();
        }
        return truckDao;
    }

    public static DriverStatusDao getDriverStatusDao() {
        if (driverStatusDao == null) {
            driverStatusDao = new DriverStatusDaoImpl();
        }
        return driverStatusDao;
    }
}
