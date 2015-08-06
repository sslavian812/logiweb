package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.model.TruckModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.Util;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


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
    public List<TruckModel> findAllTrucks() throws ServiceLayerException {
        try {
            return truckDao.findAll().stream()
                    .map(t -> new TruckModel(t))
                    .collect(Collectors.toList());
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void addTruck(TruckModel truck) throws ServiceLayerException {
        try {
            if (truckDao.findByRegistrationNumber(truck.getRegistrationNumber()) != null) {
                throw new ServiceLayerException("truck registration number already in use!");
            }
            truckDao.create(new TruckEntity(truck.getCrewSize(),
                    truck.getRegistrationNumber(),
                    truck.getCapacity(),
                    truck.getStatus()));
            LOG.info("Truck created. Registration Number: [" + truck.getRegistrationNumber() + "]");

        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public void updateTruck(TruckModel truck, String oldRegistrationNumber) throws ServiceLayerException {
        try {
            TruckEntity truckEntity = truckDao.findByRegistrationNumber(oldRegistrationNumber);

            if (truckEntity == null) {
                LOG.warn("Failed update of truck + [" + oldRegistrationNumber + "]. Truck does not exists.");
                throw new ServiceLayerException("No such truck.");
            }

            if (!oldRegistrationNumber.equals(truck.getRegistrationNumber())) {
                if (truckDao.findByRegistrationNumber(truck.getRegistrationNumber()) != null) {
                    LOG.warn("Fail to change registration Number for truck. [" + truck.getRegistrationNumber() + "] already exists.");
                    throw new ServiceLayerException("Fail to update truck. "
                            + "Truck with new registration number already exists");
                }
            }

            truckEntity.setRegistrationNumber(truck.getRegistrationNumber());
            truckEntity.setCapacity(truck.getCapacity());
            truckEntity.setCrewSize(truck.getCrewSize());
            truckEntity.setStatus(truck.getStatus());


            truckDao.update(truckEntity);
            LOG.info("Truck updated. Registration Number: [" + truck.getRegistrationNumber() + "]");

        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
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
                        "Unable to delete truck with trucks assigned to it. Drivers should be unassigned first.");
                exc.setStackTrace(Thread.currentThread().getStackTrace());

                LOG.debug("Unexpected: trying to delete already assigned truck ", exc);
                throw exc;
            }
            truckDao.delete(truck);

            LOG.info("Truck created. Registration Number " + truckRegistrationNumber);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }

    @Override
    @Transactional
    public TruckModel findTruckModelByRegistrationNumber(String registrationNumber) throws ServiceLayerException {
        try {
            return new TruckModel(truckDao.findByRegistrationNumber(registrationNumber));
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }
}
