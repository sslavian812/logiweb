package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServieceLauerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class DriverAssignmentServiceImpl implements DriverAssignmentService {

    private DriverDao driverDao;
    private OrderDao orderDao;

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public DriverAssignmentServiceImpl(DriverDao driverDao, OrderDao orderDao, EntityManager em) {
        this.driverDao = driverDao;
        this.orderDao = orderDao;
        this.em = em;
    }


    /**
     * provides "driver assignment card":
     * private String driverPersonalNumber; +
     * private List<DriverEntity> coDriverPersonalNumbers;
     * private String truckRegistrationNumber; +
     * private String orderIdentifier; +
     * private List<CargoEntity> cargos; +
     *
     * @param driverPersonalNumber
     * @return
     * @throws ServieceLauerException
     */
    @Override
    public DriverAssignment getDriverAssignment(String driverPersonalNumber) throws ServieceLauerException {
        DriverAssignment driverAssignment = new DriverAssignment();

        driverAssignment.setDriverPersonalNumber(driverPersonalNumber);

        DriverEntity driver = driverDao.findByPersonalNumber(driverPersonalNumber);
        DriverStatusEntity driverStatus = driver.getDriverStatusEntity();

        TruckEntity truck = driverStatus.getTruckEntity();
        driverAssignment.setTruckRegistrationNumber(truck.getRegistrationNumber());

        OrderEntity order = orderDao.findByTruckId(truck.getId());
        driverAssignment.setOrderIdentifier(order.getOrderIdentifier());

        List<CargoEntity> cargos = order.getCargoEntities();
        driverAssignment.setCargos(cargos);

        List<DriverEntity> coDrivers = driverDao.findByCurrentTruck(truck.getId());
        driverAssignment.setCoDrivers(coDrivers);

        return driverAssignment;
    }

    @Override
    public DriverAssignment findDriverAssignmentByPersonalNumber(String personalNumber) {
        try {
            DriverAssignment assignment = getDriverAssignment(personalNumber);
            return assignment;
        } catch (ServieceLauerException e) {
            return null;
        }
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
