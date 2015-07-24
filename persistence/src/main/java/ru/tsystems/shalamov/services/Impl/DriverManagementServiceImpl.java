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
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.transaction.Transactional;
import java.util.List;

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

    /**
     * Private string constant sed to display errors.
     */
    private static final String UNEXPECTED = "Unexpected: ";

    @Override
    @Transactional
    public List<DriverEntity> listDrivers() throws ServiceLayerException {
        try {
            return driverDao.findAll();
            // todo: is the transaction necessary here?
        } catch (DataAccessLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void addDriver(final DriverEntity driver)
            throws ServiceLayerException {
        validateForEmptyFields(driver);

        try {
            if (driverDao.
                    findByPersonalNumber(driver.getPersonalNumber()) != null) {
                throw new ServiceLayerException(
                        "personal Number already in use");
            }

            DriverStatusEntity driverStatus = new DriverStatusEntity(driver);
            driverDao.create(driver);
            driverStatusDao.create(driverStatus);

            LOG.info("Driver created. " + driver.getFirstName()
                    + " " + driver.getLastName()
                    + " PN:" + driver.getPersonalNumber());

        } catch (DataAccessLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void updateDriver(final DriverEntity driver)
            throws ServiceLayerException {
        validateForEmptyFields(driver);

        try {
            driverDao.update(driver);

            LOG.info("Driver updated. " + driver.getFirstName() + " "
                    + driver.getLastName()
                    + " PN:" + driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            LOG.warn(UNEXPECTED, e);
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
                LOG.warn(UNEXPECTED, exc);
                throw exc;
            }

            DriverStatus status = driver.getDriverStatusEntity().getStatus();
            if (status.equals(DriverStatus.PRIMARY)
                    || status.equals(DriverStatus.AUXILIARY)) {
                ServiceLayerException exc = new ServiceLayerException(
                        "Unable to delete driver, while processing order.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());
                LOG.warn(UNEXPECTED, exc);
                throw exc;
            }

            DriverStatusEntity driverStatus = driver.getDriverStatusEntity();
            driverStatusDao.delete(driverStatus);
            driverDao.delete(driver);

            LOG.info("Driver deleted. "
                    + driver.getFirstName() + " " + driver.getLastName()
                    + " PN:" + driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public boolean checkDriverExistence(final String personalNumber)
            throws ServiceLayerException {
        try {
            return driverDao.findByPersonalNumber(personalNumber) != null;
        } catch (Exception e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void updateDriverStatus(final DriverStatusEntity driverStatusEntity)
            throws ServiceLayerException {
        try {
            driverStatusDao.update(driverStatusEntity);
        } catch (DataAccessLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public DriverEntity findDriverByPersonalNumber(final String personalNumber)
            throws ServiceLayerException {
        try {
            return driverDao.findByPersonalNumber(personalNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn(UNEXPECTED, e);
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
            LOG.warn(UNEXPECTED, e);
            throw e;
        }
    }
}
