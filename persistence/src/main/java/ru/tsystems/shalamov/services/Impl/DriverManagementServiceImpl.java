package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DriverManagementServiceImpl implements DriverManagementService {
    DriverDao driverDao;

    public DriverManagementServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }


    @Override
    public List<DriverEntity> listDrivers() {
        return driverDao.findAll();
    }

    @Override
    public void addDriver(DriverEntity driver) {
        driverDao.create(driver);
    }

    @Override
    public void updateDriver(DriverEntity driver) {
        driverDao.update(driver);
    }

    @Override
    public void deleteDriverByPersonalDriver(String driverPersonalNumber) {
        driverDao.delete(driverDao.findByPersonalNumber(driverPersonalNumber));
    }

//    @Override
//    public List<DriverEntity> findAvailableDrivers() {
//        return driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder();
//    }
}
