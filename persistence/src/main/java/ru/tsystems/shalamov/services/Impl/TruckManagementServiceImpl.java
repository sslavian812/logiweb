package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class TruckManagementServiceImpl implements TruckManagementService {
    private TruckDao truckDao;

    private static final Logger LOG = Logger.getLogger(TruckManagementService.class);

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public TruckManagementServiceImpl(TruckDao truckDao, EntityManager em) {
        this.truckDao = truckDao;
        this.em = em;
    }


    @Override
    public List<TruckEntity> getAllTrucks() throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock
            List<TruckEntity> list = truckDao.findAll();
            getEntityManager().getTransaction().commit();
            return list;
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void addTruck(TruckEntity truck) throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            if (truckDao.findByRegistrationNumber(truck.getRegistrationNumber()) != null) {
                throw new ServiceLayerException("truck registration number already in use!");
            }
            truckDao.create(truck);
            getEntityManager().getTransaction().commit();
            LOG.info("Truck created. Registration Number: " + truck.getRegistrationNumber());

        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void updateTruck(TruckEntity truck) throws ServiceLayerException {
        try {
            getEntityManager().getTransaction().begin();
            truckDao.update(truck);
            getEntityManager().getTransaction().commit();
            LOG.info("Truck updated. Registration Number: " + truck.getRegistrationNumber());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void deleteTruckByRegistrationNumber(String truckRegistrationNumber)
            throws ServiceLayerException {
        try {
            TruckEntity truck = truckDao.findByRegistrationNumber(truckRegistrationNumber);
            if (truck == null) {
                ServiceLayerException exc = new ServiceLayerException(
                        "No trucks with such registration number found.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());

                LOG.debug("Unexpected: trying to delete not existing truck ", exc);
                throw exc;
            }
            if (truck.getDriverStatusEntities().size() != 0) {
                ServiceLayerException exc = new ServiceLayerException(
                        "Unable to delete truck with drivers assigned to it. Drivers should be unassigned first.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());

                LOG.debug("Unexpected: trying to delete already assigned truck ", exc);
                throw exc;
            }

            getEntityManager().getTransaction().begin();
            truckDao.delete(truck);
            getEntityManager().getTransaction().commit();

            LOG.info("Truck created. Registration Number " + truckRegistrationNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public TruckEntity findTruckByRegistrationNumber(String registrationNumber)
            throws ServiceLayerException {
        try {
            return truckDao.findByRegistrationNumber(registrationNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(registrationNumber);
        }
    }
}
