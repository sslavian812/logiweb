package ru.tsystems.shalamov.services.Impl;

import ru.tsystems.shalamov.dao.DaoFactory;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.api.ServieceLauerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class TruckManagementServiceImpl implements TruckManagementService {
    @Override
    public List<TruckEntity> getAllTrucks() {
        return DaoFactory.getTruckDao().findAll();
    }

    @Override
    public void addTruck(TruckEntity truck) {
        DaoFactory.getTruckDao().create(truck);
    }

    @Override
    public void updateTruck(TruckEntity truck) throws ServieceLauerException {
        DaoFactory.getTruckDao().update(truck);
    }

    @Override
    public void deleteTruckById(int truckId) throws ServieceLauerException {
        TruckDao truckDao = DaoFactory.getTruckDao();
        truckDao.delete(truckDao.read(truckId));
    }

    @Override
    public List<TruckEntity> findAvailableTrucks(int minimalCapacity) {
        return DaoFactory.getTruckDao().findAllByCapacity(minimalCapacity);
    }
}
