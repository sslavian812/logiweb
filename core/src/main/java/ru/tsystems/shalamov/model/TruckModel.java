package ru.tsystems.shalamov.model;

import ru.tsystems.shalamov.entities.TruckEntity;
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
        this.registrationNumber = registrationNumber;
        this.crewSize = crewSize;
        this.capacity = capacity;
        this.status = truckStatus;
    }

    public TruckModel(TruckEntity truckEntity)
    {
        this(
                truckEntity.getRegistrationNumber(),
                truckEntity.getCrewSize(),
                truckEntity.getCapacity(),
                truckEntity.getStatus());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckModel that = (TruckModel) o;

        if (capacity != that.capacity) return false;
        if (crewSize != that.crewSize) return false;
        if (status != that.status) return false;
        if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (Integer.hashCode(crewSize));
        result = 31 * result + (Integer.hashCode(capacity));
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + (orderIdentifier != null ? orderIdentifier.hashCode() : 0);
        result = 31 * result + (drivers != null ? drivers.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
