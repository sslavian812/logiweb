package ru.tsystems.shalamov.services.Impl;

import ru.tsystems.shalamov.dao.DaoFactory;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;
import ru.tsystems.shalamov.services.api.ServieceLauerException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DriverAssignmentServiceImpl implements DriverAssignmentService {
    /**
     * provides "driver assignment card":
     *     private String driverPersonalNumber; +
     *     private List<DriverEntity> coDriverPersonalNumbers;
     *     private String truckRegistrationNumber; +
     *     private String orderIdentifier; +
     *     private List<CargoEntity> cargos; +
     * @param driverPersonalNumber
     * @return
     * @throws ServieceLauerException
     */
    @Override
    public DriverAssignment getDriverAssignment(String driverPersonalNumber) throws ServieceLauerException {
        DriverAssignment driverAssignment = new DriverAssignment();

        driverAssignment.setDriverPersonalNumber(driverPersonalNumber);

        DriverEntity driver = DaoFactory.getDriverDao().findByPersonalNumber(driverPersonalNumber);
        DriverStatusEntity driverStatus = DaoFactory.getDriverStatusDao().read(driver.getId());
        TruckEntity truck = driverStatus.getTruckEntity();
        driverAssignment.setTruckRegistrationNumber(truck.getRegistrationNumber());

        OrderEntity order = DaoFactory.getOrderDao().findByTruckId(truck.getId());
        driverAssignment.setOrderIdentifier(order.getOrderIdentifier());

        List<CargoEntity> cargos = order.getCargoEntities();
        driverAssignment.setCargos(cargos);

        List<DriverEntity> coDrivers = DaoFactory.getDriverDao().findByCurrentTruck(truck.getId());
        driverAssignment.setCoDrivers(coDrivers);

        return driverAssignment;
    }

    @Override
    public List<String> getCoDriversPersonalNumbers(String driverPersonalNumber) throws ServieceLauerException {
        DriverAssignment driverAssignment = getDriverAssignment(driverPersonalNumber);
        return driverAssignment.getCoDrivers()
                .stream().map(d -> d.getPersonalNumber()).collect(Collectors.toList());
    }

    @Override
    public String getTruckRegistrationNumber(String driverPersonalNumber) throws ServieceLauerException {
        return getDriverAssignment(driverPersonalNumber).getTruckRegistrationNumber();

    }

    @Override
    public String getOrderIdentifier(String driverPersonalNumber) throws ServieceLauerException {
        return getDriverAssignment(driverPersonalNumber).getOrderIdentifier();
    }

    @Override
    public List<CargoEntity> getCargoes(String driverPersonalNumber) throws ServieceLauerException {
        return getDriverAssignment(driverPersonalNumber).getCargos();
    }

    @Override
    public List<String> getCargoesDenominations(String driverPersonalNumber) throws ServieceLauerException {
        return getDriverAssignment(driverPersonalNumber).getCargos()
                .stream().map(c -> c.getDenomination()).collect(Collectors.toList());
    }
}
