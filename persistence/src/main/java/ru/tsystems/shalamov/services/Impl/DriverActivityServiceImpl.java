package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.CargoDao;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.dao.api.ShiftDao;
import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.ShiftEntity;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverActivityService;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

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


            ShiftEntity shift = shiftDao.findActiveShiftByDriver(personalNumber);
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
            DriverEntity driver =
                    driverDao.findByPersonalNumber(personalNumber);
            if (driver == null)
                throw new ServiceLayerException("no such driver found");

            DriverStatusEntity statusEntity = driver.getDriverStatusEntity();

            if (driverStatus == DriverStatus.REST)
                throw new ServiceLayerException("status REST is " +
                        "not allowed to set directly. End you shift first.");

            if (statusEntity.getStatus() == DriverStatus.REST)
                throw new ServiceLayerException("status change is not allowed."
                        + "begin your shift first.");

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
}
