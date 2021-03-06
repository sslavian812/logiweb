package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.services.DateUtilities;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverActivityService;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 29.07.2015.
 */
@Service
public class DriverActivityServiceImpl implements DriverActivityService {

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.DriverEntity}.
     */
    @Autowired
    private DriverDao driverDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.ShiftEntity}.
     */
    @Autowired
    private ShiftDao shiftDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.DriverStatusEntity}.
     */
    @Autowired
    private DriverStatusDao driverStatusDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.CargoEntity}.
     */
    @Autowired
    private CargoDao cargoDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.OrderEntity}.
     */
    @Autowired
    private OrderDao orderDao;

    /**
     * Public constructor.
     *
     * @param driverDao       driver DAO object
     * @param shiftDao        shift DAO object
     * @param driverStatusDao driver status DAO object
     * @param cargoDao        cargo DAO object
     * @param orderDao        order DAO object
     */
    @Autowired
    public DriverActivityServiceImpl(final DriverDao driverDao, final ShiftDao shiftDao,
                                     final DriverStatusDao driverStatusDao,
                                     final CargoDao cargoDao, final OrderDao orderDao) {
        this.driverDao = driverDao;
        this.shiftDao = shiftDao;
        this.driverStatusDao = driverStatusDao;
        this.cargoDao = cargoDao;
        this.orderDao = orderDao;
    }


    /**
     * Log4j {@link org.apache.log4j.Logger}  for logging.
     */
    private static final Logger LOG =
            Logger.getLogger(DriverInfoServiceImpl.class);

    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void beginShift(final String personalNumber) throws ServiceLayerException {
        try {
            DriverEntity driver = driverDao.findByPersonalNumber(personalNumber);
            if (driver == null) {
                throw new ServiceLayerException("no such driver found");
            }


            ShiftEntity shift = shiftDao.findActiveShiftByDriver(driver);
            if (shift != null) {
                throw new ServiceLayerException("this driver is already on the shift.");
            }

            shift = new ShiftEntity();
            shift.setDriverEntity(driver);
            Date date = new Date();
            Timestamp t = new Timestamp(date.getTime());
            shift.setShiftBegin(t);
            shiftDao.create(shift);

            DriverStatusEntity statusEntity = driver.getDriverStatusEntity();
            statusEntity.setStatus(DriverStatus.AUXILIARY);
            driverStatusDao.update(statusEntity);

            LOG.info("New active shift created for driver [" + personalNumber + "]");
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public void endShift(final String personalNumber) throws ServiceLayerException {
        try {
            DriverEntity driver = driverDao.findByPersonalNumber(personalNumber);
            if (driver == null) {
                throw new ServiceLayerException("no such driver found");
            }

            // in case of unhonest drivers: if order is being set completed while
            // one of assigned drivers is not on shift
            // workaround - if status is REST throw no exception, just exit.
            if (driver.getDriverStatusEntity().getStatus().equals(DriverStatus.REST)) {
                LOG.warn("Truing to end shift for driver [" + personalNumber + "]. No shift exists.");
                return;
            }

            ShiftEntity shift = shiftDao.findActiveShiftByDriver(driver);
            if (shift == null) {
                throw new ServiceLayerException("this driver has no active shift now!");
            }


            Date date = new Date();
            Timestamp t = new Timestamp(date.getTime());
            shift.setShiftEnd(t);
            shiftDao.update(shift);

            DriverStatusEntity statusEntity = driver.getDriverStatusEntity();
            statusEntity.setStatus(DriverStatus.REST);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(shift.getShiftBegin());
            int startMonth = calendar.get(Calendar.MONTH);
            calendar.setTime(shift.getShiftEnd());
            int endMonth = calendar.get(Calendar.MONTH);
            if (startMonth == endMonth) {
                statusEntity.setWorkingHours(
                        statusEntity.getWorkingHours() + DateUtilities.diffInHours(
                                shift.getShiftBegin(), shift.getShiftEnd()
                        ));
            } else {
                statusEntity.setWorkingHours(
                        statusEntity.getWorkingHours() + DateUtilities.diffInHours(
                                DateUtilities.getFirstDayOfMonthDate(shift.getShiftEnd()), shift.getShiftEnd()
                        ));
                statusEntity.setLastMonth(endMonth);
            }

            driverStatusDao.update(statusEntity);
            LOG.info("Shift ended for driver [" + personalNumber + "]");
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void driverStatusChanged(
            final String personalNumber, final DriverStatus driverStatus)
            throws ServiceLayerException {
        try {
            if (driverStatus == DriverStatus.REST) {
                throw new ServiceLayerException("status REST is "
                        + "not allowed to manipulate directly. End you shift first.");
            }

            DriverEntity driver =
                    driverDao.findByPersonalNumber(personalNumber);
            if (driver == null) {
                throw new ServiceLayerException("no such driver found");
            }

            DriverStatusEntity statusEntity = driver.getDriverStatusEntity();


            if (statusEntity.getStatus() == DriverStatus.REST) {
                throw new ServiceLayerException("status REST is not allowed to manipulate directly."
                        + " Begin your shift first.");
            }

            String oldStatus = statusEntity.getStatus().toString();
            statusEntity.setStatus(driverStatus);
            driverStatusDao.update(statusEntity);

            LOG.info("Driver [" + personalNumber + "] status changed from "
                    + oldStatus + " to " + driverStatus);
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void cargoStatusChanged(
            final String cargoIdentifier, final CargoStatus cargoStatus)
            throws ServiceLayerException {
        try {
            CargoEntity cargoEntity = cargoDao.findCargoByCargoIdentifier(cargoIdentifier);
            if (cargoEntity == null) {
                throw new ServiceLayerException("no such cargo found");
            }

            String oldStatus = cargoEntity.getStatus().toString();
            cargoEntity.setStatus(cargoStatus);
            cargoDao.update(cargoEntity);

            LOG.info("Cargo [" + cargoIdentifier + "] status changed from "
                    + oldStatus + " to " + cargoStatus);
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void completeOrder(final String orderIdentifier) throws ServiceLayerException {
        try {
            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);
            if (order == null) {
                throw new ServiceLayerException("no such order");
            }

            if (!order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
                throw new ServiceLayerException("order has " + order.getStatus()
                        + " status. Unable to set it to " + OrderStatus.COMPLETED);
            }

            if (order.getCargoEntities().stream()
                    .filter(c -> c.getStatus().equals(CargoStatus.DELIVERED))
                    .collect(Collectors.toList()).isEmpty()) {
                ServiceLayerException e = new ServiceLayerException("unable to complete order, not all cargoes are delivered");
                e.setStackTrace(Thread.currentThread().getStackTrace());
                LOG.warn(e);
                throw e;
            }

            TruckEntity truck = order.getTruckEntity();
            List<DriverStatusEntity> driverStatuses = truck.getDriverStatusEntities();

            for (DriverStatusEntity d : driverStatuses) {
                endShift(d.getDriverEntity().getPersonalNumber());
                d.setStatus(DriverStatus.UNASSIGNED);
                d.setTruckEntity(null);
            }

            truck.setDriverStatusEntities(null);
            truck.setStatus(TruckStatus.INTACT);
            order.setStatus(OrderStatus.COMPLETED);

            LOG.info("Order [" + orderIdentifier
                    + "] has been completed. All drivers statuses set to UNASSIGNED");
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }
}
