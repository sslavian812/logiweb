package ru.tsystems.shalamov.model;

import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.services.DriverAssignment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple POJO class, aggregating information about assignment.
 * Created by viacheslav on 01.07.2015.
 */
public class DriverAssignmentModel {
    /**
     * Id of driver, which requested the assignment information.
     */
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
    private String driverPersonalNumber;

    @NotNull
    private List<DriverModel> coDrivers;

    @Pattern(regexp = "[a-zA-Z]{2}[0-9]{5}", message = "two letters and 5 digits")
    private String truckRegistrationNumber;

    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
    private String orderIdentifier;

    private List<CargoModel> cargoes;

    @NotNull
    private DriverStatus driverStatus;

    public DriverAssignmentModel() {
        driverPersonalNumber=null;
        coDrivers = new ArrayList<>();
        truckRegistrationNumber = null;
        orderIdentifier = null;
        cargoes = new ArrayList<>();
        driverStatus = DriverStatus.UNASSIGNED;
    }


    public DriverAssignmentModel(String driverPersonalNumber,
                                 String truckRegistrationNumber,
                                 String orderIdentifier,
                                 DriverStatus driverStatus,
                                 List<CargoModel> cargoModelList,
                                 List<DriverModel> driverModelList
    ) {

        this.driverPersonalNumber = driverPersonalNumber;
        this.truckRegistrationNumber = truckRegistrationNumber;
        this.orderIdentifier = orderIdentifier;
        this.driverStatus = driverStatus;
        this.cargoes = cargoModelList;
        this.coDrivers = driverModelList;

    }

    public DriverAssignmentModel(DriverAssignment assignment) {
        this.driverPersonalNumber = assignment.getDriverPersonalNumber();
        this.truckRegistrationNumber = assignment.getTruckRegistrationNumber();
        this.orderIdentifier = assignment.getOrderIdentifier();
        this.driverStatus = assignment.getDriverStatus();

        this.cargoes = assignment.getCargos().stream().map(c -> new CargoModel(c)).collect(Collectors.toList());
        this.coDrivers = assignment.getCoDrivers().stream().map(d -> new DriverModel(d)).collect(Collectors.toList());
    }

    public String getDriverPersonalNumber() {
        return driverPersonalNumber;
    }

    public void setDriverPersonalNumber(String driverPersonalNumber) {
        this.driverPersonalNumber = driverPersonalNumber;
    }

    public List<DriverModel> getCoDrivers() {
        return coDrivers;
    }

    public void setCoDrivers(List<DriverModel> coDrivers) {
        this.coDrivers = coDrivers;
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

    public List<CargoModel> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoModel> cargoes) {
        this.cargoes = cargoes;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }
}
