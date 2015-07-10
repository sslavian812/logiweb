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


    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private CargoDao cargoDao;
    private DriverDao driverDao;
    private DriverStatusDao driverStatusDao;
    private OrderDao orderDao;
    private ShiftDao shiftDao;
    private TruckDao truckDao;


    private DriverActivityService driverActivityService;
    private DriverAssignmentService driverAssignmentService;
    private DriverManagementService driverManagementService;
    private OrderManagementService orderManagementService;
    private TruckManagementService truckManagementService;


    private ApplicationContext() {
    }

    private synchronized void createEmfInstance() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            createEmfInstance();
        }
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public CargoDao getCargoDao() {
        if (cargoDao == null) {
            cargoDao = new CargoDaoImpl(getEntityManager());
        }
        return cargoDao;
    }

    public DriverDao getDriverDao() {
        if (driverDao == null) {
            driverDao = new DriverDaoImpl(getEntityManager());
        }
        return driverDao;
    }

    public ShiftDao geShiftDao() {
        if (shiftDao == null) {
            shiftDao = new ShiftDaoImpl(getEntityManager());
        }
        return shiftDao;
    }

    public TruckDao getTruckDao() {
        if (truckDao == null) {
            truckDao = new TruckDaoImpl(getEntityManager());
        }
        return truckDao;
    }

    public DriverStatusDao getDriverStatusDao() {
        if (driverStatusDao == null)
            driverStatusDao = new DriverStatusDaoImpl(getEntityManager());
        return driverStatusDao;
    }

    public OrderDao getOrderDao() {
        if (orderDao == null)
            orderDao = new OrderDaoImpl(getEntityManager());
        return orderDao;
    }

    public DriverActivityService getDriverActivityService() {
        // todo: null for now. should be impemented in part 2;
        return driverActivityService;
    }

    public DriverAssignmentService getDriverAssignmentService() {
        if (driverAssignmentService == null)
            driverAssignmentService = new DriverAssignmentServiceImpl(
                    getDriverDao(), getOrderDao(), getEntityManager());
        return driverAssignmentService;
    }

    public DriverManagementService getDriverManagementService() {
        if (driverManagementService == null)
            driverManagementService = new DriverManagementServiceImpl(
                    getDriverDao(), getDriverStatusDao(), getEntityManager());
        return driverManagementService;
    }

    public OrderManagementService getOrderManagementService() {
        if (orderManagementService == null)
            orderManagementService = new OrderManagementServiceImpl(
                    getDriverDao(), getOrderDao(), getTruckDao(),
                    getDriverStatusDao(), getCargoDao(), getEntityManager());
        return orderManagementService;
    }

    public TruckManagementService getTruckManagementService() {
        if (truckManagementService == null)
            truckManagementService = new TruckManagementServiceImpl(getTruckDao(), getEntityManager());
        return truckManagementService;
    }
}