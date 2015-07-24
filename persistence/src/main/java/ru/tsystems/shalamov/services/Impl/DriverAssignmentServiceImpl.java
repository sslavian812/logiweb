package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by viacheslav on 01.07.2015.
 */
@Service
public class DriverAssignmentServiceImpl implements DriverAssignmentService {

    /**
     * Public constructor.
     *
     * @param driverDao DAO for
     *                  {@link ru.tsystems.shalamov.entities.DriverEntity}
     * @param orderDao  DAO for
     *                  {@link ru.tsystems.shalamov.entities.OrderEntity}
     */
    @Autowired
    public DriverAssignmentServiceImpl(final DriverDao driverDao,
                                       final OrderDao orderDao) {

        this.driverDao = driverDao;
        this.orderDao = orderDao;
    }

    /**
     * DAO object for Driver entity.
     */
    private DriverDao driverDao;

    /**
     * DAO object for Order entity.
     */
    private OrderDao orderDao;

    /**
     * Log4j {@link org.apache.log4j.Logger}  for logging.
     */
    private static final Logger LOG = Logger.getLogger(
            DriverAssignmentServiceImpl.class);



    /**
     * provides "driver assignment card". Card Consists of:
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
    @Transactional
    public DriverAssignment getDriverAssignmentByPersonalNumber(
            final String driverPersonalNumber) throws ServiceLayerException {
        try {
            DriverAssignment driverAssignment = new DriverAssignment();

            driverAssignment.setDriverPersonalNumber(driverPersonalNumber);

            DriverEntity driver =
                    driverDao.findByPersonalNumber(driverPersonalNumber);
            if (driver == null) {
                return null;
            }
            DriverStatusEntity driverStatus = driver.getDriverStatusEntity();

            TruckEntity truck = driverStatus.getTruckEntity();
            if (truck == null) {
                return null;
            }
            driverAssignment.setTruckRegistrationNumber(
                    truck.getRegistrationNumber());

            OrderEntity order = orderDao.findByTruckId(truck.getId());
            if (order == null) {
                return null;
            }
            driverAssignment.setOrderIdentifier(order.getOrderIdentifier());

            List<CargoEntity> cargos = order.getCargoEntities();
            driverAssignment.setCargos(cargos);

            List<DriverEntity> coDrivers =
                    driverDao.findByCurrentTruck(truck.getId());
            driverAssignment.setCoDrivers(coDrivers);


            LOG.debug("Driver assignment information "
                    + "successfully got from database");
            return driverAssignment;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    public List<String> getCoDriversPersonalNumbers(
            final String driverPersonalNumber) throws ServiceLayerException {
        DriverAssignment driverAssignment =
                getDriverAssignmentByPersonalNumber(driverPersonalNumber);
        return driverAssignment.getCoDrivers()
                .stream().map(d -> d.getPersonalNumber())
                .collect(Collectors.toList());
    }

    @Override
    public String getTruckRegistrationNumber(final String driverPersonalNumber)
            throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber)
                .getTruckRegistrationNumber();

    }

    @Override
    public String getOrderIdentifier(final String driverPersonalNumber)
            throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber)
                .getOrderIdentifier();
    }

    @Override
    public List<CargoEntity> getCargoes(final String driverPersonalNumber)
            throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber)
                .getCargos();
    }

    @Override
    public List<String> getCargoesDenominations(
            final String driverPersonalNumber) throws ServiceLayerException {
        return getDriverAssignmentByPersonalNumber(driverPersonalNumber)
                .getCargos()
                .stream().map(c -> c.getDenomination())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<DriverAssignment> findAllDriverAssignments()
            throws ServiceLayerException {
        try {
            List<OrderEntity> orders = orderDao.findAll();

            List<DriverAssignment> assignments = new ArrayList<>(orders.size());
            for (OrderEntity o : orders) {
                DriverAssignment assignment =
                        findDriverAssignmentByOrderIdentifier(
                                o.getOrderIdentifier());

                if (assignment != null) {
                    assignments.add(assignment);
                }
            }

            return assignments;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public DriverAssignment findDriverAssignmentByOrderIdentifier(
            final String orderIdentifier) throws ServiceLayerException {
        try {
            DriverAssignment driverAssignment = new DriverAssignment();
            driverAssignment.setDriverPersonalNumber(null);

            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);
            if (order == null) {
                return null;
            }
            driverAssignment.setOrderIdentifier(order.getOrderIdentifier());

            TruckEntity truck = order.getTruckEntity();
            if (truck == null) {
                return null;
            }
            driverAssignment.setTruckRegistrationNumber(
                    truck.getRegistrationNumber());

            List<CargoEntity> cargos = order.getCargoEntities();
            driverAssignment.setCargos(cargos);

            List<DriverEntity> coDrivers =
                    driverDao.findByCurrentTruck(truck.getId());
            driverAssignment.setCoDrivers(coDrivers);

            return driverAssignment;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }
}
