package ru.tsystems.shalamov.OnBoardUnit;

import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by viacheslav on 25.07.2015.
 */
@ManagedBean
public class DriverInfoBean {
    DriverStatus driverStatus;
    String personalNumber;

    String orderIdentifier;

    String truckRegistrationNumber;
    List<String> involvedDrivers = new ArrayList<>();
    HashMap<String, CargoStatus> cargoes = new HashMap<>();



    public String getAssignmentInforamation() {
        // when called, PN should be already set.
        if (personalNumber == null || personalNumber.isEmpty())
            return "fail";

        // todo query WS and set all fields;

        // fake data
        driverStatus = DriverStatus.PRIMARY;
        orderIdentifier = "some order";
        truckRegistrationNumber = "some truck";
        involvedDrivers.add(personalNumber);
        cargoes.put("some_cargo", CargoStatus.PREPARED);
        cargoes.put("some_other_cargo", CargoStatus.SHIPPED);

        return "success";
    }

    public String swapStatus() {
       // this.personalNumber = personalNumber;
        getAssignmentInforamation();

        if (driverStatus != DriverStatus.REST) {
            if (driverStatus == DriverStatus.AUXILIARY)
                driverStatus = DriverStatus.PRIMARY;
            else
                driverStatus = DriverStatus.AUXILIARY;
        }

        // todo query WS to change driverStatus

        return "updated";
    }

    public String swapShift() {
      //  this.personalNumber = personalNumber;
        getAssignmentInforamation();

        if (driverStatus == DriverStatus.REST) {
            driverStatus = DriverStatus.PRIMARY;
        } else {
            driverStatus = DriverStatus.REST;
        }

        // todo query WS to change driverStatus

        return "updated";
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public List<String> getInvolvedDrivers() {
        return involvedDrivers;
    }

    public void setInvolvedDrivers(List<String> involvedDrivers) {
        this.involvedDrivers = involvedDrivers;
    }

    public HashMap<String, CargoStatus> getCargoes() {
        return cargoes;
    }

    public void setCargoes(HashMap<String, CargoStatus> cargoes) {
        this.cargoes = cargoes;
    }

    public String getTruckRegistrationNumber() {
        return truckRegistrationNumber;
    }

    public void setTruckRegistrationNumber(String truckRegistrationNumber) {
        this.truckRegistrationNumber = truckRegistrationNumber;
    }
}
