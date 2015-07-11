package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DriverAssignmentServiceImpl implements DriverAssignmentService {

    private DriverDao driverDao;
    private OrderDao orderDao;

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public DriverAssignmentServiceImpl(DriverDao driverDao, OrderDao orderDao, EntityManager em) {
        this.driverDao = driverDao;
        this.orderDao = orderDao;
        this.em = em;
    }


    /**
     * provides "driver assignment card":
     * private String driverPersonalNumber; +
     * private List<DriverEntity> coDriverPersonalNumbers;
     * private String truckRegistrationNumber; +
     * private String orderIdentifier; +
     * private List<CargoEntity> cargos; +
     *
     * @param driverPersonalNumber
     * @return
     * @throws ru.tsystems.shalamov.services.ServiceLayerException
     */
    @Override
    public DriverAssignment getDriverAssignmentByPersonalNumber(String driverPersonalNumber) throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin(); // just lock to database to diasllow changes.
            DriverAssignment driverAssignment = new DriverAssignment();

            driverAssignment.setDriverPersonalNumber(driverPersonalNumber);

            DriverEntity driver = driverDao.findByPersonalNumber(driverPersonalNumber);
            DriverStatusEntity driverStatus = driver.getDriverStatusEntity();

            TruckEntity truck = driverStatus.getTruckEntity();
            driverAssignment.setTruckRegistrationNumber(truck.getRegistrationNumber());

            OrderEntity order = orderDao.findByTruckId(truck.getId());
            driverAssignment.setOrderIdentifier(order.getOrderIdentifier());

            List<CargoEntity> cargos = order.getCargoEntities();
            driverAssignment.setCargos(cargos);

            List<DriverEntity> coDrivers = driverDao.findByCurrentTruck(truck.getId());
            driverAssignment.setCoDrivers(coDrivers);

            getEntityManager().getTransaction().commit();
            return driverAssignment;
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();

        }
    }

    @Override
    public List<String> getCoDriversPersonalNumbers(String driverPersonalNumber) throws ServiceLayerException {
        DriverAssignment driverAssignment = getDriverAssignmentByPersonalNumber(driverPersonalNumber);
        return driverAssignment.getCoDrivers()
                .stream().map(d -> d.getPersonalNumber()).collect(Collectors.toList());
    }

    @Override
    public String getTruckRegistrationNumber(String driverPersonalNumber) throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber).getTruckRegistrationNumber();

    }

    @Override
    public String getOrderIdentifier(String driverPersonalNumber) throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber).getOrderIdentifier();
    }

    @Override
    public List<CargoEntity> getCargoes(String driverPersonalNumber) throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber).getCargos();
    }

    @Override
    public List<String> getCargoesDenominations(String driverPersonalNumber) throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber).getCargos()
                .stream().map(c -> c.getDenomination()).collect(Collectors.toList());
    }

    @Override
    public List<DriverAssignment> findAllDriverAssignments() throws ServiceLayerException {
        try {
            List<OrderEntity> orders = orderDao.findAll();
            //return orders.stream().map(o -> findDriverAssignmentByOrderIdentifier(o.getOrderIdentifier()));

            List<DriverAssignment> assignments = new ArrayList<>(orders.size());
            for (OrderEntity o : orders) {
                DriverAssignment assignment = findDriverAssignmentByOrderIdentifier(o.getOrderIdentifier());
                if (assignment != null)
                    assignments.add(assignment);
            }

            return assignments;
        } catch (DataAccessLayerException e) {
            // todo log;
            throw new ServiceLayerException(e);
        }
    }

    @Override
    public DriverAssignment findDriverAssignmentByOrderIdentifier(String orderIdentifier) throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin(); // just lock to database to diasllow changes.
            DriverAssignment driverAssignment = new DriverAssignment();
            driverAssignment.setDriverPersonalNumber(null);

            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);
            if (order == null) {
                getEntityManager().getTransaction().rollback();
                return null;
            }
            driverAssignment.setOrderIdentifier(order.getOrderIdentifier());

            TruckEntity truck = order.getTruckEntity();
            if (truck == null) {
                getEntityManager().getTransaction().rollback();
                return null;
            }
            driverAssignment.setTruckRegistrationNumber(truck.getRegistrationNumber());

            List<CargoEntity> cargos = order.getCargoEntities();
            driverAssignment.setCargos(cargos);

            List<DriverEntity> coDrivers = driverDao.findByCurrentTruck(truck.getId());
            driverAssignment.setCoDrivers(coDrivers);

            getEntityManager().getTransaction().commit();
            return driverAssignment;
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }
}
