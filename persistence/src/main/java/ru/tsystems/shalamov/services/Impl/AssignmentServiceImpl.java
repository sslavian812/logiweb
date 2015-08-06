package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.model.AvailableToAssignModel;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.model.TruckModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.Util;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 05.08.2015.
 */
@Service
public class AssignmentServiceImpl implements ru.tsystems.shalamov.services.api.AssignmentService {

    private DriverDao driverDao;
    private TruckDao truckDao;
    private DriverStatusDao driverStatusDao;
    private OrderDao orderDao;

    private static final Logger LOG = Logger.getLogger(AssignmentServiceImpl.class);


    @Autowired
    public AssignmentServiceImpl(DriverDao driverDao, OrderDao orderDao,
                                 TruckDao truckDao, DriverStatusDao driverStatusDao,
                                 CargoDao cargoDao) {
        this.driverDao = driverDao;
        this.orderDao = orderDao;
        this.truckDao = truckDao;
        this.driverStatusDao = driverStatusDao;
    }


    @Override
    @Transactional
    public AvailableToAssignModel findAvailableToAssign(String orderIdentifier) throws ServiceLayerException {
        try {
            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);

            if (order == null) {
                LOG.warn("Failed to get available trucks and drivers for order: no such order");
                throw new ServiceLayerException("no such order [" + orderIdentifier + "]");
            }

            if (!order.getStatus().equals(OrderStatus.UNASSIGNED)) {
                throw new ServiceLayerException("Unable to assign. Order is already assigned.");
            }

            List<TruckModel> availableTrucks = findTrucksForOrder(order).stream()
                    .map(t -> new TruckModel(t)).collect(Collectors.toList());

            List<DriverModel> availableDrivers = findDriversForOrder(order).stream()
                    .map(d -> new DriverModel(d)).collect(Collectors.toList());

            availableTrucks = availableTrucks.subList(0, Math.min(10, availableTrucks.size()));
            availableDrivers = availableDrivers.subList(0, Math.min(10, availableDrivers.size()));

            return new AvailableToAssignModel(orderIdentifier, availableDrivers, availableTrucks);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void assignDriversAndTruckToOrder(AvailableToAssignModel availableToAssignModel) throws ServiceLayerException {
        try {
            int availableCrew = availableToAssignModel.getChosenDriverPersonalNumbers().size();
            TruckEntity truck = truckDao.findByRegistrationNumber(
                    availableToAssignModel.getChosenTruckRegistrationNumber());

            if (truck == null) {
                LOG.warn("invalid truck provided for assignment: "
                        + availableToAssignModel.getChosenTruckRegistrationNumber());
                throw new ServiceLayerException("Unable to assign. No such truck.");
            }

            int requiredCrewSize = truck.getCrewSize();
            if (requiredCrewSize > availableCrew) {
                LOG.warn("Insufficient crew for truck provided: " +
                        requiredCrewSize + " vs " + truck.getCrewSize());
                throw new ServiceLayerException("Unable to assign. Need more drivers for the truck.");
            }

            OrderEntity order = orderDao.findByOrderIdentifier(
                    availableToAssignModel.getOrderIdentifier());

            if (truck.getCapacity() < order.getTotalweight()) {
                LOG.warn("insufficient truck capacity: " + truck.getCapacity()
                        + " vs " + order.getTotalweight());
                throw new ServiceLayerException("unable to assign. Insufficient capacity");
            }

            List<DriverEntity> drivers = new ArrayList<>(requiredCrewSize);

            for (int i = 0; i < requiredCrewSize; ++i) {
                DriverEntity driver = driverDao.findByPersonalNumber(
                        availableToAssignModel.getChosenDriverPersonalNumbers().get(i));
                drivers.add(driver);
            }

            assignDriversAndTruckToOrder(drivers, truck, order);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }


    @Transactional
    private List<TruckEntity> findTrucksForOrder(OrderEntity order)
            throws ServiceLayerException {
        try {
            return truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(order.getTotalweight());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Transactional
    private List<DriverEntity> findDriversForOrder(OrderEntity order)
            throws ServiceLayerException {
        try {
            return driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder();
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Transactional
    private void assignDriversAndTruckToOrder(List<DriverEntity> drivers,
                                              TruckEntity truck, OrderEntity order)
            throws ServiceLayerException {

        if (truck.getDriverStatusEntities().size() != 0) {
            throw new ServiceLayerException("truck ["
                    + truck.getRegistrationNumber() + "] is already in use");
        }

        drivers = drivers.stream()
                .filter(d -> d.getDriverStatusEntity().getStatus() == DriverStatus.UNASSIGNED)
                .collect(Collectors.toList());

        int crewSize = truck.getCrewSize();
        if (drivers.size() < crewSize) {
            throw new ServiceLayerException("not enough drivers provided to assign as crew");
        }

        try {
            order.setTruckEntity(truck);
            order.setStatus(OrderStatus.IN_PROGRESS);
            orderDao.update(order);

            drivers.subList(0, crewSize);

            for (Iterator<DriverEntity> it = drivers.iterator(); it.hasNext(); ) {
                DriverStatusEntity driverStatus = it.next().getDriverStatusEntity();
                driverStatus.setTruckEntity(truck);
                driverStatus.setStatus(DriverStatus.REST);
                driverStatusDao.update(driverStatus);
            }

            String assignedDrivers = drivers.stream()
                    .map(d -> d.getPersonalNumber())
                    .reduce((a, b) -> a + ", " + b).get();

            LOG.info("Assignment: order:[" + order.getOrderIdentifier()
                    + "] trucks:[" + truck.getRegistrationNumber()
                    + "] drivers:{" + assignedDrivers + "}");
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }


}
