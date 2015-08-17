package ru.tsystems.shalamov.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
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
    /**
     * DAO object for {@link ru.tsystems.shalamov.entities.TruckEntity}.
     */
    private TruckDao truckDao;

    /**
     * Log4j {@link org.apache.log4j.Logger}  for logging.
     */
    private static final Logger LOG = Logger.getLogger(
            TruckManagementService.class);

    /**
     * Public constructor
     *
     * @param truckDao truck DAO object.
     */
    @Autowired
    public TruckManagementServiceImpl(final TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final List<TruckModel> findAllTrucks() throws ServiceLayerException {
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
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void addTruck(final TruckModel truck) throws ServiceLayerException {
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
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void updateTruck(final TruckModel truck, final String oldRegistrationNumber) throws ServiceLayerException {
        try {
            TruckEntity truckEntity = truckDao.findByRegistrationNumber(oldRegistrationNumber);

            if (truckEntity == null) {
                LOG.warn("Failed update of truck + [" + oldRegistrationNumber + "]. Truck does not exists.");
                throw new ServiceLayerException("No such truck.");
            }

            if ((!oldRegistrationNumber.equals(truck.getRegistrationNumber()))
                    && truckDao.findByRegistrationNumber(truck.getRegistrationNumber()) != null) {
                LOG.warn("Fail to change registration Number for truck. [" + truck.getRegistrationNumber() + "] already exists.");
                throw new ServiceLayerException("Fail to update truck. "
                        + "Truck with new registration number already exists");
            }

            if (truckEntity.getStatus().equals(TruckStatus.ASSIGNED)
                    && truck.getStatus() != truckEntity.getStatus()) {
                throw new ServiceLayerException("Fail to change truck status while processing order");
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
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final void deleteTruckByRegistrationNumber(final String truckRegistrationNumber)
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

            //no need of && !truck.getDriverStatusEntities().isEmpty()
            if (truck.getDriverStatusEntities() != null) {
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
    @Transactional(rollbackOn = ServiceLayerException.class)
    public final TruckModel findTruckModelByRegistrationNumber(final String registrationNumber) throws ServiceLayerException {
        try {
            TruckEntity truckEntity = truckDao.findByRegistrationNumber(registrationNumber);
            if (truckEntity == null) {
                return null;
            }
            return new TruckModel(truckEntity);
        } catch (DataAccessLayerException e) {
            LOG.warn(Util.UNEXPECTED, e);
            throw new ServiceLayerException(e);
        }
    }
}
