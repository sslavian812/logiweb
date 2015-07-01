package ru.tsystems.shalamov.services.Impl;

import ru.tsystems.shalamov.dao.DaoFactory;
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
        return DaoFactory.getDriverDao().findAll();
    }

    @Override
    public void addDriver(DriverEntity driver) {
        DaoFactory.getDriverDao().create(driver);
    }

    @Override
    public void updateDriver(DriverEntity driver) {
        DaoFactory.getDriverDao().update(driver);
    }

    @Override
    public void deleteDriverById(int driverId) {
        DriverDao driverDao = DaoFactory.getDriverDao();
        driverDao.delete(driverDao.read(driverId));
    }

    @Override
    public List<DriverEntity> findAvailableDrivers() {
        return DaoFactory.getDriverDao().findAvailable();
    }
}
