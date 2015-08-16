package ru.tsystems.shalamov.model;

import java.util.List;

/**
 * Created by viacheslav on 04.08.2015.
 */
public class AvailableToAssignModel {

    private List<TruckModel> availableTrucks;
    private List<DriverModel> availableDrivers;

    private String orderIdentifier;

    private String chosenTruckRegistrationNumber;
    private List<String> chosenDriverPersonalNumbers;


    public AvailableToAssignModel(String orderIdentifier,
                                  String chosenTruckRegistrationNumber,
                                  List<String> chosenDriverPersonalNumbers) {
        this.orderIdentifier = orderIdentifier;
        this.chosenTruckRegistrationNumber = chosenTruckRegistrationNumber;
        this.chosenDriverPersonalNumbers = chosenDriverPersonalNumbers;
    }

    public AvailableToAssignModel(String orderIdentifier,
                                  List<DriverModel> availableDrivers,
                                  List<TruckModel> availableTrucks) {
        this.orderIdentifier = orderIdentifier;
        this.availableDrivers = availableDrivers;
        this.availableTrucks = availableTrucks;
    }

    public List<TruckModel> getAvailableTrucks() {
        return availableTrucks;
    }

    public void setAvailableTrucks(List<TruckModel> availableTrucks) {
        this.availableTrucks = availableTrucks;
    }

    public List<DriverModel> getAvailableDrivers() {
        return availableDrivers;
    }

    public void setAvailableDrivers(List<DriverModel> availableDrivers) {
        this.availableDrivers = availableDrivers;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public String getChosenTruckRegistrationNumber() {
        return chosenTruckRegistrationNumber;
    }

    public void setChosenTruckRegistrationNumber(String chosenTruckRegistrationNumber) {
        this.chosenTruckRegistrationNumber = chosenTruckRegistrationNumber;
    }

    public List<String> getChosenDriverPersonalNumbers() {
        return chosenDriverPersonalNumbers;
    }

    public void setChosenDriverPersonalNumbers(List<String> chosenDriverPersonalNumbers) {
        this.chosenDriverPersonalNumbers = chosenDriverPersonalNumbers;
    }

}
