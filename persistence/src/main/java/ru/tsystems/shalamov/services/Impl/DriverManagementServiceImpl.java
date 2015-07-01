package ru.tsystems.shalamov.services.Impl;

import ru.tsystems.shalamov.dao.DaoProvider;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DriverManagementServiceImpl implements DriverManagementService {
    @Override
    public List<DriverEntity> listDrivers() {
        return DaoProvider.getDriverDao().findAll();
    }

    @Override
    public void addDriver(DriverEntity driver) {
        DaoProvider.getDriverDao().create(driver);
    }

    @Override
    public void updateDriver(DriverEntity driver) {
        DaoProvider.getDriverDao().update(driver);
    }

    @Override
    public void deleteDriverById(int driverId) {
        DriverDao driverDao = DaoProvider.getDriverDao();
        driverDao.delete(driverDao.read(driverId));
    }

    @Override
    public List<DriverEntity> findAvailableDrivers() {
        return DaoProvider.getDriverDao().findAvailable();
    }
}
