package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.model.CargoModel;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverInfoService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 01.07.2015.
 */
@Service
public class DriverInfoServiceImpl implements DriverInfoService {
    public static final String NONE = "NONE";

    /**
     * Public constructor.
     *
     * @param driverDao DAO for
     *                  {@link ru.tsystems.shalamov.entities.DriverEntity}
     * @param orderDao  DAO for
     *                  {@link ru.tsystems.shalamov.entities.OrderEntity}
     */
    @Autowired
    public DriverInfoServiceImpl(final DriverDao driverDao,
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
            DriverInfoServiceImpl.class);

    @Override
    @Transactional
    public List<DriverAssignmentModel> findAllDriverAssignments() throws ServiceLayerException {
        try {
            List<OrderEntity> orders = orderDao.findAll();

            List<DriverAssignmentModel> assignments = new ArrayList<>(orders.size());
            for (OrderEntity o : orders) {
                DriverAssignmentModel assignment =
                        findDriverAssignmentModelByOrderIdentifier(
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
    public DriverAssignmentModel findDriverAssignmentModelByPersonalNumber(String driverPersonalNumber)
            throws ServiceLayerException {
        DriverAssignmentModel assignmentModel = getPossibleInformationForDriver(driverPersonalNumber);
        if (assignmentModel.getTruckRegistrationNumber() == null)
            return null;
        else
            return assignmentModel;
    }

    @Override
    @Transactional
    public DriverAssignmentModel getPossibleInformationForDriver(String driverPersonalNumber)
            throws ServiceLayerException {
        try {
            DriverAssignmentModel driverAssignment = new DriverAssignmentModel();
            driverAssignment.setDriverPersonalNumber(driverPersonalNumber);

            DriverEntity driver = driverDao.findByPersonalNumber(driverPersonalNumber);
            if (driver == null) {
                LOG.warn("Querying not existing driver.[" + driverPersonalNumber + "]");
                throw new ServiceLayerException("no such driver");
            }

            DriverStatusEntity driverStatusEntity = driver.getDriverStatusEntity();
            driverAssignment.setDriverStatus(driverStatusEntity.getStatus());
            TruckEntity truck = driverStatusEntity.getTruckEntity();

            if (truck == null) {
                driverAssignment.setTruckRegistrationNumber(NONE);
                driverAssignment.setOrderIdentifier(NONE);
                LOG.info("Driver Information with no assigned Truck and Order returned.");
                return driverAssignment;
            }

            driverAssignment.setTruckRegistrationNumber(truck.getRegistrationNumber());
            OrderEntity order = orderDao.findByTruckId(truck.getId());
            if (order == null) {
                LOG.error(" no order for active truck ["
                        + truck.getRegistrationNumber() + "] found. Data is inconsistent.");
                throw new ServiceLayerException("no order for active truck ["
                        + truck.getRegistrationNumber() + "] found. ");
            }

            driverAssignment.setOrderIdentifier(order.getOrderIdentifier());

            List<CargoEntity> cargos = order.getCargoEntities();
            driverAssignment.setCargoes(cargos.stream()
                    .map(c -> new CargoModel(c)).collect(Collectors.toList()));

            List<DriverEntity> coDrivers =
                    driverDao.findByCurrentTruck(truck.getId());
            driverAssignment.setCoDrivers(coDrivers.stream()
                    .map(d -> new DriverModel(d)).collect(Collectors.toList()));


            LOG.info("Driver assignment information returned for active driver.");
            return driverAssignment;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }



    @Transactional
    private DriverAssignmentModel findDriverAssignmentModelByOrderIdentifier(
            final String orderIdentifier) throws ServiceLayerException {
        try {
            DriverAssignmentModel driverAssignment = new DriverAssignmentModel();
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

            List<CargoModel> cargoes = order.getCargoEntities().stream()
                    .map(c -> new CargoModel(c)).collect(Collectors.toList());
            driverAssignment.setCargoes(cargoes);

            List<DriverEntity> coDrivers = driverDao.findByCurrentTruck(truck.getId());
            driverAssignment.setCoDrivers(coDrivers.stream()
                    .map(c -> new DriverModel(c)).collect(Collectors.toList()));

            return driverAssignment;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }
}
