package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.model.AvailableToAssignModel;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.model.TruckModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.Util;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 05.08.2015.
 */
@Service
public class AssignmentServiceImpl implements ru.tsystems.shalamov.services.api.AssignmentService {

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.DriverEntity}.
     */
    private DriverDao driverDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.TruckEntity}.
     */
    private TruckDao truckDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.DriverStatusEntity}.
     */
    private DriverStatusDao driverStatusDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.OrderEntity}.
     */
    private OrderDao orderDao;

    /**
     * Log4j {@link org.apache.log4j.Logger}  for logging.
     */
    private static final Logger LOG = Logger.getLogger(AssignmentServiceImpl.class);

    /**
     * Max amount of drivers, displayed on manager's scree when he to choose crew for a truck.
     */
    public static final int MAX = 10;


    /**
     * Public constructor.
     *
     * @param driverDao       driver DAO object
     * @param orderDao        order DAO object
     * @param truckDao        truck dao object
     * @param driverStatusDao driver status dao object
     */
    @Autowired
    public AssignmentServiceImpl(final DriverDao driverDao, final OrderDao orderDao,
                                 final TruckDao truckDao, final DriverStatusDao driverStatusDao) {
        this.driverDao = driverDao;
        this.orderDao = orderDao;
        this.truckDao = truckDao;
        this.driverStatusDao = driverStatusDao;
    }


    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final AvailableToAssignModel findAvailableToAssign(final String orderIdentifier) throws ServiceLayerException {
        try {
            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);

            if (order == null) {
                LOG.warn("Failed to get available trucks and drivers for order: no such order");
                throw new ServiceLayerException("no such order [" + orderIdentifier + "]");
            }

            if (!order.getStatus().equals(OrderStatus.UNASSIGNED)) {
                throw new ServiceLayerException("Order is not available to be assigned. Already Assigned.");
            }

            List<TruckModel> availableTrucks =
                    truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(order.getTotalweight()).stream()
                            .map(t -> new TruckModel(t)).collect(Collectors.toList());

            List<DriverModel> availableDrivers =
                    driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder().stream()
                            .map(d -> new DriverModel(d)).collect(Collectors.toList());

            availableTrucks = availableTrucks.subList(0, Math.min(MAX, availableTrucks.size()));
            availableDrivers = availableDrivers.subList(0, Math.min(MAX, availableDrivers.size()));

            return new AvailableToAssignModel(orderIdentifier, availableDrivers, availableTrucks);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void assignDriversAndTruckToOrder(final AvailableToAssignModel availableToAssignModel) throws ServiceLayerException {
        try {
            TruckEntity truck = truckDao.findByRegistrationNumber(
                    availableToAssignModel.getChosenTruckRegistrationNumber());

            if (truck == null) {
                LOG.warn("invalid truck provided for assignment: "
                        + availableToAssignModel.getChosenTruckRegistrationNumber());
                throw new ServiceLayerException("Unable to assign. No such truck.");
            }

            if (!truck.getDriverStatusEntities().isEmpty()) {
                throw new ServiceLayerException("truck ["
                        + truck.getRegistrationNumber() + "] is already in use");
            }

            OrderEntity order = orderDao.findByOrderIdentifier(
                    availableToAssignModel.getOrderIdentifier());

            if (truck.getCapacity() < order.getTotalweight()) {
                LOG.warn("insufficient truck capacity: " + truck.getCapacity()
                        + " vs " + order.getTotalweight());
                throw new ServiceLayerException("unable to assign. Insufficient capacity");
            }

            List<DriverEntity> drivers = new ArrayList<>();

            for (String d : availableToAssignModel.getChosenDriverPersonalNumbers()) {
                DriverEntity driver = driverDao.findByPersonalNumber(d);
                if (driver.getDriverStatusEntity().getStatus() == DriverStatus.UNASSIGNED) {
                    drivers.add(driver);
                }
            }

            if (drivers.size() < truck.getCrewSize()) {
                LOG.warn("Insufficient crew for truck provided: "
                        + drivers.size() + " instead of at least " + truck.getCrewSize());
                throw new ServiceLayerException("not enough drivers provided to assign as crew");
            }

            drivers.subList(0, truck.getCrewSize());

            assignDriversAndTruckToOrder(drivers, truck, order);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    /**
     * Private method for internal use. Directly assigns
     * given driver entities and truck entity to the order entity.
     *
     * @param drivers driver entities
     * @param truck   truck entities
     * @param order   order entities
     * @throws ServiceLayerException if something unexpected occur in database.
     */
    @Transactional(rollbackOn = ServiceLayerException.class)
    private void assignDriversAndTruckToOrder(final List<DriverEntity> drivers,
                                              final TruckEntity truck, final OrderEntity order)
            throws ServiceLayerException {
        try {

            order.setTruckEntity(truck);
            order.setStatus(OrderStatus.IN_PROGRESS);
            orderDao.update(order);

            truck.setStatus(TruckStatus.ASSIGNED);
            truckDao.update(truck);


            for (DriverEntity d : drivers) {
                DriverStatusEntity driverStatus = d.getDriverStatusEntity();
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
