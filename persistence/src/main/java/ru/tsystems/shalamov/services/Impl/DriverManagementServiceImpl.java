package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DriverManagementServiceImpl implements DriverManagementService {
    private DriverDao driverDao;
    private DriverStatusDao driverStatusDao;

    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(DriverManagementServiceImpl.class);

    private EntityManager getEntityManager() {
        return em;
    }

    public DriverManagementServiceImpl(
            DriverDao driverDao, DriverStatusDao driverStatusDao, EntityManager em) {
        this.driverDao = driverDao;
        this.driverStatusDao = driverStatusDao;
        this.em = em;
    }


    @Override
    public List<DriverEntity> listDrivers() throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock
            List<DriverEntity> list = driverDao.findAll();
            getEntityManager().getTransaction().commit();
            return list;
            // todo: is the transaction necessary here?
            //return driverDao.findAll();
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void addDriver(DriverEntity driver) throws ServiceLayerException {
        validateForEmptyFields(driver);

        try {
            getEntityManager().getTransaction().begin();

            if (driverDao.findByPersonalNumber(driver.getPersonalNumber()) != null)
                throw new ServiceLayerException("personal Number already in use");

            DriverStatusEntity driverStatus = new DriverStatusEntity(driver);
            driverDao.create(driver);
            driverStatusDao.create(driverStatus);

            getEntityManager().getTransaction().commit();

            LOG.info("Driver created. " + driver.getFirstName() + " "
                    + driver.getLastName() + " PN:" + driver.getPersonalNumber());

        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void updateDriver(DriverEntity driver) throws ServiceLayerException {
        validateForEmptyFields(driver);

        try {
            getEntityManager().getTransaction().begin();
            driverDao.update(driver);
            getEntityManager().getTransaction().commit();

            LOG.info("Driver updated. " + driver.getFirstName() + " "
                    + driver.getLastName() + " PN:" + driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void deleteDriverByPersonalNumber(String driverPersonalNumber)
            throws ServiceLayerException {
        try {
            DriverEntity driver = driverDao.findByPersonalNumber(driverPersonalNumber);
            if (driver == null) {
                ServiceLayerException exc = new ServiceLayerException(
                        "No drivers with such personal number found.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());
                LOG.warn("Unexpected: ", exc);
                throw exc;
            }

            DriverStatus status = driver.getDriverStatusEntity().getStatus();
            if (status.equals(DriverStatus.PRIMARY) || status.equals(DriverStatus.AUXILIARY)) {
                ServiceLayerException exc = new ServiceLayerException(
                        "Unable to delete driver, while processing order. Try later.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());
                LOG.warn("Unexpected: ", exc);
                throw exc;
            }

            getEntityManager().getTransaction().begin();

            DriverStatusEntity driverStatus = driver.getDriverStatusEntity();
            driverStatusDao.delete(driverStatus);
            driverDao.delete(driver);

            getEntityManager().getTransaction().commit();

            LOG.info("Driver deleted. " + driver.getFirstName() + " "
                    + driver.getLastName() + " PN:" + driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public boolean checkDriverExistence(String personalNumber)
            throws ServiceLayerException {
        try {
            return driverDao.findByPersonalNumber(personalNumber) != null;
        } catch (Exception e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    public void updateDriverStatus(DriverStatusEntity driverStatusEntity)
            throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            driverStatusDao.update(driverStatusEntity);
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public DriverEntity findDriverByPersonalNumber(String personalNumber)
            throws ServiceLayerException {
        try {
            return driverDao.findByPersonalNumber(personalNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    private void validateForEmptyFields(DriverEntity d)
            throws ServiceLayerException {
        try {
            if (d.getFirstName() == null || d.getFirstName().isEmpty()) {
                throw new ServiceLayerException("Driver's first name can't be empty.");
            } else if (d.getLastName() == null || d.getLastName().isEmpty()) {
                throw new ServiceLayerException("Driver's last name can't be empty.");
            } else if (d.getPersonalNumber() == null || d.getPersonalNumber().isEmpty()) {
                throw new ServiceLayerException("Personal number not set.");
            }
        } catch (ServiceLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw e;
        }
    }
}
