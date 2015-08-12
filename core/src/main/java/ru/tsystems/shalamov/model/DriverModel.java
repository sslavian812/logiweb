package ru.tsystems.shalamov.model;

import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by viacheslav on 22.06.2015.
 */
public class DriverModel {

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+", message = "alphabetic characters only")
    private String firstName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+", message = "alphabetic characters only")
    private String lastName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
    private String personalNumber;

    @NotNull
    private DriverStatus driverStatus;


//    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
//    private String orderIdentifier;

    @Pattern(regexp = "[a-zA-Z0-9]{2}[0-9]{5}]", message = "two letters and five digits")
    private String truckRegistrationNumber;

    public DriverModel(String firstName, String lastName,
                       String personalNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.driverStatus = DriverStatus.UNASSIGNED;
    }

    public DriverModel(String firstName, String lastName,
                       String personalNumber, DriverStatus driverStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.driverStatus = driverStatus;
    }

    public DriverModel(String firstName, String lastName,
                       String personalNumber, DriverStatus driverStatus,
                       String truckRegistrationNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.driverStatus = driverStatus;
        this.truckRegistrationNumber = truckRegistrationNumber;
    }

    public DriverModel(DriverEntity driverEntity) {
        this.firstName = driverEntity.getFirstName();
        this.lastName = driverEntity.getLastName();
        this.personalNumber = driverEntity.getPersonalNumber();

        DriverStatusEntity t = driverEntity.getDriverStatusEntity();
        if (t == null)
            this.driverStatus = DriverStatus.UNASSIGNED;
        else
            this.driverStatus = t.getStatus();

        if (t!= null  && t.getTruckEntity() != null) {
            this.truckRegistrationNumber = t.getTruckEntity().getRegistrationNumber();
        } else
            truckRegistrationNumber = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

//    public String getOrderIdentifier() {
//        return orderIdentifier;
//    }
//
//    public void setOrderIdentifier(String orderIdentifier) {
//        this.orderIdentifier = orderIdentifier;
//    }

    public String getTruckRegistrationNumber() {
        return truckRegistrationNumber;
    }

    public void setTruckRegistrationNumber(String truckRegistrationNumber) {
        this.truckRegistrationNumber = truckRegistrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverModel that = (DriverModel) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (personalNumber != null ? !personalNumber.equals(that.personalNumber) : that.personalNumber != null)
            return false;

        return true;
    }
}
