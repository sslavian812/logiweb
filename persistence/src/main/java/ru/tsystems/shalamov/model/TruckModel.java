package ru.tsystems.shalamov.model;

import ru.tsystems.shalamov.entities.statuses.TruckStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by viacheslav on 22.06.2015.
 */
public class TruckModel {

    @NotNull
    @Pattern(regexp = "[a-zA-Z]{2}[0-9]{5}", message = "two letters and 5 digits")
    private String registrationNumber;

    @NotNull
    @Size(min = 1, max = 10, message = "crew size should be at least 1 and at most 10")
    private int crewSize;

    @NotNull
    @Size(min = 0, max = 100000, message = "truck capacity should be non-negative and at most 100 000 kg")
    private int capacity;

    @NotNull
    private TruckStatus status;


    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
    private String orderIdentifier;

    private List<String> drivers;


    public TruckModel(String registrationNumber, int crewSize, int capacity, TruckStatus truckStatus) {
        this.capacity = capacity;
        this.crewSize = crewSize;
        this.status = truckStatus;
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<String> drivers) {
        this.drivers = drivers;
    }
}
