package ru.tsystems.shalamov.services.Impl;

import ru.tsystems.shalamov.dao.DaoProvider;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServieceLauerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class TruckManagementServiceImpl implements TruckManagementService {
    @Override
    public List<TruckEntity> getAllTrucks() {
        return DaoProvider.getTruckDao().findAll();
    }

    @Override
    public void addTruck(TruckEntity truck) {
        DaoProvider.getTruckDao().create(truck);
    }

    @Override
    public void updateTruck(TruckEntity truck) throws ServieceLauerException {
        DaoProvider.getTruckDao().update(truck);
    }

    @Override
    public void deleteTruckById(int truckId) throws ServieceLauerException {
        TruckDao truckDao = DaoProvider.getTruckDao();
        truckDao.delete(truckDao.read(truckId));
    }

    @Override
    public List<TruckEntity> findAvailableTrucks(int minimalCapacity) {
        return DaoProvider.getTruckDao().findAllByCapacity(minimalCapacity);
    }
}
