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
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverActivityService;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by viacheslav on 29.07.2015.
 */
@Service
public class DriverActivityServiceImpl implements DriverActivityService {
    @Autowired
    private DriverDao driverDao;
    @Autowired
    private ShiftDao shiftDao;
    @Autowired
    private DriverStatusDao driverStatusDao;
    @Autowired
    private CargoDao cargoDao;

    @Autowired
    private OrderDao orderDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public DriverActivityServiceImpl(DriverDao driverDao, ShiftDao shiftDao, DriverStatusDao driverStatusDao, CargoDao cargoDao) {
        this.driverDao = driverDao;
        this.shiftDao = shiftDao;
        this.driverStatusDao = driverStatusDao;
        this.cargoDao = cargoDao;
    }


    /**
     * Log4j {@link org.apache.log4j.Logger}  for logging.
     */
    private static final Logger LOG =
            Logger.getLogger(DriverInfoServiceImpl.class);

    @Override
    @Transactional
    public void beginShift(String personalNumber) throws ServiceLayerException {
        try {
            DriverEntity driver = driverDao.findByPersonalNumber(personalNumber);
            if (driver == null)
                throw new ServiceLayerException("no such driver found");


            ShiftEntity shift = shiftDao.findActiveShiftByDriver(driver);
            if (shift != null)
                throw new ServiceLayerException("this driver is already on the shift.");

            shift = new ShiftEntity();
            shift.setDriverEntity(driver);
            Date date = new Date();
            Timestamp t = new Timestamp(date.getTime());
            shift.setShiftBegin(t);
            shiftDao.create(shift);

            DriverStatusEntity statusEntity = driver.getDriverStatusEntity();
            statusEntity.setStatus(DriverStatus.PRIMARY);
            driverStatusDao.update(statusEntity);

            LOG.info("New active shift created for driver [" + personalNumber + "]");
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void endShift(String personalNumber) throws ServiceLayerException {
        try {
            DriverEntity driver = driverDao.findByPersonalNumber(personalNumber);
            if (driver == null)
                throw new ServiceLayerException("no such driver found");

            ShiftEntity shift = shiftDao.findActiveShiftByDriver(driver);
            if (shift == null)
                throw new ServiceLayerException("this driver has no active shift now!");


            Date date = new Date();
            Timestamp t = new Timestamp(date.getTime());
            shift.setShiftEnd(t);
            shiftDao.update(shift);

            DriverStatusEntity statusEntity = driver.getDriverStatusEntity();
            statusEntity.setStatus(DriverStatus.REST);
            driverStatusDao.update(statusEntity);
            LOG.info("Shift ended for driver [" + personalNumber + "]");
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void driverStatusChanged(
            String personalNumber, DriverStatus driverStatus)
            throws ServiceLayerException {
        try {
            if (driverStatus == DriverStatus.REST)
                throw new ServiceLayerException("status REST is " +
                        "not allowed to manipulate directly. End you shift first.");


            DriverEntity driver =
                    driverDao.findByPersonalNumber(personalNumber);
            if (driver == null)
                throw new ServiceLayerException("no such driver found");

            DriverStatusEntity statusEntity = driver.getDriverStatusEntity();


            if (statusEntity.getStatus() == DriverStatus.REST)
                throw new ServiceLayerException("status REST is not allowed to manipulate directly."
                        + " Begin your shift first.");

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
    @Transactional
    public void cargoStatusChanged(
            String cargoIdentifier, CargoStatus cargoStatus)
            throws ServiceLayerException {
        try {
            CargoEntity cargoEntity = cargoDao.findCargoByCargoIdentifier(cargoIdentifier);
            if (cargoEntity == null)
                throw new ServiceLayerException("no such cargo found");

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
    @Transactional
    public void completeOrder(String orderIdentifier) throws ServiceLayerException {
        try {

            // todo test this methos
            OrderEntity order = orderDao.findByOrderIdentifier(orderIdentifier);
            if (order == null) {
                throw new ServiceLayerException("no suck order");
            }

            if (!order.getStatus().equals(OrderStatus.IN_PROGRESS)) {
                throw new ServiceLayerException("order has " + order.getStatus() + " status. Unable to set it to " + OrderStatus.COMPLETED);
            }

            TruckEntity truck = order.getTruckEntity();
            List<DriverStatusEntity> driverStatuses = truck.getDriverStatusEntities();

            for (DriverStatusEntity d : driverStatuses) {
                endShift(d.getDriverEntity().getPersonalNumber());
                d.setStatus(DriverStatus.UNASSIGNED);
                d.setTruckEntity(null);
            }

            truck.setDriverStatusEntities(null);
            order.setStatus(OrderStatus.COMPLETED);

            LOG.info("Order [" + orderIdentifier + "] has been completed. All drivers statuses set to UNASSIGNED");
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }
}