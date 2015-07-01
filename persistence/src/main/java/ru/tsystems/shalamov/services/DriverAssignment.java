package ru.tsystems.shalamov.services;

import com.sun.istack.internal.Nullable;
import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.DriverEntity;

import java.util.List;

/**
 * Simple POJO class, aggregating information about assignment.
 * Created by viacheslav on 01.07.2015.
 */
public class DriverAssignment {
    /**
     * Id of driver, which requested the assignment information. May be null.
     */
    @Nullable
    private String driverPersonalNumber;

    private List<DriverEntity> coDrivers;
    private String truckRegistrationNumber;
    private String orderIdentifier;
    private List<CargoEntity> cargos;

    public String getDriverPersonalNumber() {
        return driverPersonalNumber;
    }

    public void setDriverPersonalNumber(String driverPersonalNumber) {
        this.driverPersonalNumber = driverPersonalNumber;
    }

    public List<DriverEntity> getCoDrivers() {
        return coDrivers;
    }

    public void setCoDrivers(List<DriverEntity> coDriverPersonalNumbers) {
        this.coDrivers = coDriverPersonalNumbers;
    }

    public String getTruckRegistrationNumber() {
        return truckRegistrationNumber;
    }

    public void setTruckRegistrationNumber(String truckRegistrationNumber) {
        this.truckRegistrationNumber = truckRegistrationNumber;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public List<CargoEntity> getCargos() {
        return cargos;
    }

    public void setCargos(List<CargoEntity> cargos) {
        this.cargos = cargos;
    }
}