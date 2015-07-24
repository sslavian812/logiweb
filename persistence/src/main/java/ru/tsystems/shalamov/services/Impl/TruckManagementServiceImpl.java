package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by viacheslav on 01.07.2015.
 */
@Service
public class TruckManagementServiceImpl implements TruckManagementService {

    @Autowired
    public TruckManagementServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    private TruckDao truckDao;

    private static final Logger LOG = Logger.getLogger(
            TruckManagementService.class);


    @Override
    @Transactional
    public List<TruckEntity> listTrucks() throws ServiceLayerException {
        try {
            return truckDao.findAll();
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void addTruck(TruckEntity truck) throws ServiceLayerException {
        try {
            if (truckDao.findByRegistrationNumber(truck.getRegistrationNumber()) != null) {
                throw new ServiceLayerException("truck registration number already in use!");
            }
            truckDao.create(truck);
            LOG.info("Truck created. Registration Number: " + truck.getRegistrationNumber());

        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void updateTruck(TruckEntity truck) throws ServiceLayerException {
        try {
            truckDao.update(truck);
            LOG.info("Truck updated. Registration Number: " + truck.getRegistrationNumber());
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
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
            // todo: figure out, why sometimes truck.getDriverStatusEntities may produce null;
            if (truck.getDriverStatusEntities() != null && truck.getDriverStatusEntities().size() != 0) {
                ServiceLayerException exc = new ServiceLayerException(
                        "Unable to delete truck with drivers assigned to it. Drivers should be unassigned first.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());

                LOG.debug("Unexpected: trying to delete already assigned truck ", exc);
                throw exc;
            }
            truckDao.delete(truck);

            LOG.info("Truck created. Registration Number " + truckRegistrationNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public TruckEntity findTruckByRegistrationNumber(String registrationNumber)
            throws ServiceLayerException {
        try {
            return truckDao.findByRegistrationNumber(registrationNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn("Unexpected: ", e);
            throw new ServiceLayerException(e);
        }
    }
}
