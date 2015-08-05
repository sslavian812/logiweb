package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.Util;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 01.07.2015.
 */
@Service
public class DriverManagementServiceImpl implements DriverManagementService {

    /**
     * Public constructor.
     *
     * @param driverDao       DAO object for
     *                        {@link ru.tsystems.shalamov.entities.DriverEntity}.
     * @param driverStatusDao DAO object for
     *                        {@link ru.tsystems.shalamov.entities.DriverStatusEntity}.
     */
    @Autowired
    public DriverManagementServiceImpl(final DriverDao driverDao,
                                       final DriverStatusDao driverStatusDao) {
        this.driverDao = driverDao;
        this.driverStatusDao = driverStatusDao;
    }

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.DriverEntity}.
     */
    private DriverDao driverDao;

    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.DriverStatusEntity}.
     */
    private DriverStatusDao driverStatusDao;

    /**
     * Log4j {@link org.apache.log4j.Logger}  for logging.
     */
    private static final Logger LOG =
            Logger.getLogger(DriverManagementServiceImpl.class);





    @Override
    @Transactional
    public List<DriverModel> findAllDrivers() throws ServiceLayerException {
        try {
            return driverDao.findAll().stream()
                    .map(d -> new DriverModel(d)).collect(Collectors.toList());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void addDriver(DriverModel driver) throws ServiceLayerException {
        DriverEntity driverEntity = new DriverEntity(driver.getFirstName(), driver.getLastName(), driver.getPersonalNumber());
        validateForEmptyFields(driverEntity);

        try {
            if (driverDao.
                    findByPersonalNumber(driver.getPersonalNumber()) != null) {
                LOG.info("failed to create driver [" + driver.getPersonalNumber()
                        + "]. Personal number already in use.");
                throw new ServiceLayerException(
                        "personal number already in use");
            }


            DriverStatusEntity driverStatus = new DriverStatusEntity(driverEntity);
            driverDao.create(driverEntity);
            driverStatusDao.create(driverStatus);

            LOG.info("Driver created. " + driver.getFirstName()
                    + " " + driver.getLastName()
                    + "  [" + driver.getPersonalNumber()+ "]");

        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void updateDriver(DriverModel driver, String oldPersonalNumber) throws ServiceLayerException {
        DriverEntity driverEntity = findDriverByPersonalNumber(oldPersonalNumber);

        if (driverEntity == null) {
            LOG.warn("Failed update of driver + ["+oldPersonalNumber+"]. Driver does not exists.");
            throw new ServiceLayerException("No such driver.");
        }

        if ((!oldPersonalNumber.equals(driver.getPersonalNumber()))
                && checkDriverExists(driver.getTruckRegistrationNumber())) {
            LOG.warn("Fail to change personal number for driver. ["+driver.getPersonalNumber()+"] already exists.");
            throw new ServiceLayerException("Fail to update driver. "
                    + "Driver with new personal number already exists");
        }

        driverEntity.setFirstName(driver.getFirstName());
        driverEntity.setLastName(driver.getLastName());
        driverEntity.setPersonalNumber(driver.getPersonalNumber());

        try {
            driverDao.update(driverEntity);

            LOG.info("Driver updated. " + driver.getFirstName() + " "
                    + driver.getLastName()
                    + "  [" + driver.getPersonalNumber()+"]");
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void deleteDriverByPersonalNumber(final String driverPersonalNumber)
            throws ServiceLayerException {
        try {
            DriverEntity driver =
                    driverDao.findByPersonalNumber(driverPersonalNumber);
            if (driver == null) {
                ServiceLayerException exc = new ServiceLayerException(
                        "No drivers with such personal number found.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());
                LOG.warn(Util.UNEXPECTED, exc);
                throw exc;
            }

            DriverStatus status = driver.getDriverStatusEntity().getStatus();
            if (status.equals(DriverStatus.PRIMARY)
                    || status.equals(DriverStatus.AUXILIARY)) {
                ServiceLayerException exc = new ServiceLayerException(
                        "Unable to delete driver, while processing order.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());
                LOG.warn(Util.UNEXPECTED, exc);
                throw exc;
            }

            DriverStatusEntity driverStatus = driver.getDriverStatusEntity();
            driverStatusDao.delete(driverStatus);
            driverDao.delete(driver);

            LOG.info("Driver deleted. "
                    + driver.getFirstName() + " " + driver.getLastName()
                    + " PN:" + driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public boolean checkDriverExists(final String personalNumber)
            throws ServiceLayerException {
        try {
            return driverDao.findByPersonalNumber(personalNumber) != null;
        } catch (Exception e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }


    @Override
    @Transactional
    public DriverModel findDriverModelByPersonalNumber(String personalNumber) throws ServiceLayerException {

        try {
            return new DriverModel(driverDao.findByPersonalNumber(personalNumber));
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }





    @Transactional
    private void updateDriverStatus(final DriverStatusEntity driverStatusEntity)
            throws ServiceLayerException {
        try {
            driverStatusDao.update(driverStatusEntity);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Transactional
    private DriverEntity findDriverByPersonalNumber(final String personalNumber)
            throws ServiceLayerException {
        try {
            return driverDao.findByPersonalNumber(personalNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    /**
     * Checks given {@link ru.tsystems.shalamov.entities.DriverEntity} fields
     * to be not empty.
     *
     * @param d {@link ru.tsystems.shalamov.entities.DriverEntity} to check
     * @throws ServiceLayerException if there are empty fields.
     */
    private void validateForEmptyFields(final DriverEntity d)
            throws ServiceLayerException {
        try {
            if (d.getFirstName() == null || d.getFirstName().isEmpty()) {
                throw new ServiceLayerException(
                        "Driver's first name can't be empty.");
            } else if (d.getLastName() == null || d.getLastName().isEmpty()) {
                throw new ServiceLayerException(
                        "Driver's last name can't be empty.");
            } else if (d.getPersonalNumber() == null
                    || d.getPersonalNumber().isEmpty()) {
                throw new ServiceLayerException("Personal number not set.");
            }
        } catch (ServiceLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw e;
        }
    }
}
