package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServieceLauerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class TruckManagementServiceImpl implements TruckManagementService {
    TruckDao truckDao;

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public TruckManagementServiceImpl(TruckDao truckDao, EntityManager em) {
        this.truckDao = truckDao;
        this.em = em;
    }


    @Override
    public List<TruckEntity> getAllTrucks() {
        return truckDao.findAll();
    }

    @Override
    public void addTruck(TruckEntity truck) {
        truckDao.create(truck);
    }

    @Override
    public void updateTruck(TruckEntity truck) throws ServieceLauerException {
        truckDao.update(truck);
    }

    @Override
    public void deleteTruckByRegistrationNumber(String truckRegistrationNumber) throws ServieceLauerException {
        truckDao.delete(truckDao.findByRegistrationNumber(truckRegistrationNumber));
    }

//    @Override
//    public List<TruckEntity> findAvailableTrucks(int minimalCapacity) {
//        return truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(minimalCapacity);
//    }
}
