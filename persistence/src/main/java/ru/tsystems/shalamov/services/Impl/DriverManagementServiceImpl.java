package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.ServiceLauerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DriverManagementServiceImpl implements DriverManagementService {
    DriverDao driverDao;

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public DriverManagementServiceImpl(DriverDao driverDao, EntityManager em) {
        this.driverDao = driverDao;
        this.em = em;
    }


    @Override
    public List<DriverEntity> listDrivers() throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock
            List<DriverEntity> list = driverDao.findAll();
            getEntityManager().getTransaction().commit();
            return list;
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void addDriver(DriverEntity driver) throws ServiceLauerException {
        validateForEmptyFields(driver);

        try {
            getEntityManager().getTransaction().begin();

            if (driverDao.findByPersonalNumber(driver.getPersonalNumber()) != null)
                throw new ServiceLauerException("personal Number already in use");

            driverDao.create(driver);

            getEntityManager().getTransaction().commit();

        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void updateDriver(DriverEntity driver) throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin();
            driverDao.update(driver);
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void deleteDriverByPersonalDriver(String driverPersonalNumber) throws ServiceLauerException {
        try {
            if (driverDao.findByPersonalNumber(driverPersonalNumber) != null)
                throw new ServiceLauerException("no drivers with such personal number found");
            getEntityManager().getTransaction().begin();
            driverDao.delete(driverDao.findByPersonalNumber(driverPersonalNumber));
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public boolean checkDriverExistence(String personalNumber) throws ServiceLauerException {
        try {
            if (driverDao.findByPersonalNumber(personalNumber) == null)
                return false;
            else
                return false;
        } catch (Exception e) {
            throw new ServiceLauerException(e);
        } finally {
        }
    }

    private void validateForEmptyFields(DriverEntity d) throws ServiceLauerException {
        if (d.getId() <= 0) {
            throw new ServiceLauerException("Driver ID can't be less or equal zero.");
        } else if (d.getFirstName() == null || d.getFirstName().isEmpty()) {
            throw new ServiceLauerException("Driver's first name can't be empty.");
        } else if (d.getLastName() == null || d.getLastName().isEmpty()) {
            throw new ServiceLauerException("Driver's last name can't be empty.");
        } else if (d.getPersonalNumber() == null || d.getDriverStatusEntity().getId() == 0) {
            throw new ServiceLauerException("Personal number not set.");
        } else if (d.getDriverStatusEntity() == null) {
            throw new ServiceLauerException("Status not set.");
        }
    }
}
