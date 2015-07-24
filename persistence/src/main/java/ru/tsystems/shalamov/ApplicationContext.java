package ru.tsystems.shalamov;

import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.dao.impl.*;
import ru.tsystems.shalamov.services.api.*;
import ru.tsystems.shalamov.services.impl.DriverAssignmentServiceImpl;
import ru.tsystems.shalamov.services.impl.DriverManagementServiceImpl;
import ru.tsystems.shalamov.services.impl.OrderManagementServiceImpl;
import ru.tsystems.shalamov.services.impl.TruckManagementServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by viacheslav on 04.07.2015.
 */
public final class ApplicationContext {

    // volatile way:
//    private static volatile ApplicationContext INSTANCE = null;
//
//    public static ApplicationContext getInstance() {
//        if (INSTANCE == null)
//            synchronized (ApplicationContext.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new ApplicationContext();
//                }
//            }
//        return INSTANCE;
//    }

    // lazy holder way:
    private static class LazyHolder {
        public static final ApplicationContext INSTANCE = new ApplicationContext();
    }

    public static ApplicationContext getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static final String PERSISTENCE_UNIT = "logiweb";
    public static final String ROLE = "role";
    public static final String ROLE_MANAGER = "manager";
    public static final String ROLE_DRIVER = "driver";


//    private volatile EntityManagerFactory entityManagerFactory;
//    private volatile EntityManager entityManager;
//
//    private volatile CargoDao cargoDao;
//    private volatile DriverDao driverDao;
//    private volatile DriverStatusDao driverStatusDao;
//    private volatile OrderDao orderDao;
//    private volatile ShiftDao shiftDao;
//    private volatile TruckDao truckDao;
//
//
//    private volatile DriverActivityService driverActivityService;
//    private volatile DriverAssignmentService driverAssignmentService;
//    private volatile DriverManagementService driverManagementService;
//    private volatile OrderManagementService orderManagementService;
//    private volatile TruckManagementService truckManagementService;


    private ApplicationContext() {
    }

//    public EntityManagerFactory getEntityManagerFactory() {
//        if (entityManagerFactory == null) {
//            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
//        }
//        return entityManagerFactory;
//    }
//
//    public EntityManager getEntityManager() {
//        if (entityManager == null || !entityManager.isOpen())
//            synchronized (this) {
//                if (entityManager == null || !entityManager.isOpen()) {
//                    entityManager = getEntityManagerFactory().createEntityManager();
//                }
//            }
//        return entityManager;
//    }
//
//    public CargoDao getCargoDao() {
//        if (cargoDao == null) synchronized (this) {
//            if (cargoDao == null) {
//                cargoDao = new CargoDaoImpl(getEntityManager());
//            }
//        }
//        return cargoDao;
//    }
//
//    public DriverDao getDriverDao() {
//        if (driverDao == null) synchronized (this) {
//            if (driverDao == null) {
//                driverDao = new DriverDaoImpl(getEntityManager());
//            }
//        }
//        return driverDao;
//    }
//
//    public ShiftDao geShiftDao() {
//        if (shiftDao == null) synchronized (this) {
//            if (shiftDao == null) {
//                shiftDao = new ShiftDaoImpl(getEntityManager());
//            }
//        }
//        return shiftDao;
//    }
//
//    public TruckDao getTruckDao() {
//        if (truckDao == null) synchronized (this) {
//            if (truckDao == null) {
//                truckDao = new TruckDaoImpl(getEntityManager());
//            }
//        }
//        return truckDao;
//    }
//
//    public DriverStatusDao getDriverStatusDao() {
//        if (driverStatusDao == null) synchronized (this) {
//            if (driverStatusDao == null) {
//                driverStatusDao = new DriverStatusDaoImpl(getEntityManager());
//            }
//        }
//        return driverStatusDao;
//    }
//
//    public OrderDao getOrderDao() {
//        if (orderDao == null) synchronized (this) {
//            if (orderDao == null) {
//                orderDao = new OrderDaoImpl(getEntityManager());
//            }
//        }
//        return orderDao;
//    }
//
//    public DriverActivityService getDriverActivityService() {
//        // todo: null for now. should be impemented in part 2;
//        return driverActivityService;
//    }
//
//    public DriverAssignmentService getDriverAssignmentService() {
//        if (driverAssignmentService == null) synchronized (this) {
//            if (driverAssignmentService == null) {
//                driverAssignmentService = new DriverAssignmentServiceImpl(
//                        getDriverDao(), getOrderDao(), getEntityManager());
//            }
//        }
//        return driverAssignmentService;
//    }
//
//    public DriverManagementService getDriverManagementService() {
//        if (driverManagementService == null) synchronized (this) {
//            if (driverManagementService == null) {
//                driverManagementService = new DriverManagementServiceImpl(
//                        getDriverDao(), getDriverStatusDao(), getEntityManager());
//            }
//        }
//        return driverManagementService;
//    }
//
//    public OrderManagementService getOrderManagementService() {
//        if (orderManagementService == null) synchronized (this) {
//            if (orderManagementService == null) {
//                orderManagementService = new OrderManagementServiceImpl(
//                        getDriverDao(), getOrderDao(), getTruckDao(),
//                        getDriverStatusDao(), getCargoDao(), getEntityManager());
//            }
//        }
//        return orderManagementService;
//    }
//
//    public TruckManagementService getTruckManagementService() {
//        if (truckManagementService == null) synchronized (this) {
//            if (truckManagementService == null) {
//                truckManagementService = new TruckManagementServiceImpl(
//                        getTruckDao(), getEntityManager());
//            }
//        }
//        return truckManagementService;
//    }
}