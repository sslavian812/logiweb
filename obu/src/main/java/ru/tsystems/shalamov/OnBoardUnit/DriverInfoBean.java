package ru.tsystems.shalamov.onBoardUnit;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.ws.DriverActivityWebService;
import ru.tsystems.shalamov.ws.DriverActivityWebServiceImplService;
import ru.tsystems.shalamov.ws.DriverAssignment;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 25.07.2015.
 */
@ManagedBean
@SessionScoped
public class DriverInfoBean {

    @Autowired
    DriverActivityWebServiceImplService webService;

    // todo сделать model
    DriverStatus driverStatus;

    @NotNull(message = "!!!!!!!!!!")
    String personalNumber;

    String orderIdentifier;

    String truckRegistrationNumber;
    List<String> involvedDrivers = new ArrayList<>();

    List<String> cargoesList = new ArrayList<>();


    List<CargoStatus> cargoesStatuses = new ArrayList<>();


    public String getAssignmentInforamation() {
        // when called, PN should be already set.
        if (personalNumber == null || personalNumber.isEmpty())
            return "fail";

        webService = new DriverActivityWebServiceImplService();
        DriverActivityWebService client = webService.getDriverActivityWebServiceImplPort();
        DriverAssignment assignment = client.driverAssignmentInformation(personalNumber);

        driverStatus = DriverStatus.valueOf(assignment.getDriverStatus().value());

        orderIdentifier = assignment.getOrderIdentifier();
        truckRegistrationNumber = assignment.getTruckRegistrationNumber();
        involvedDrivers = assignment.getCoDrivers().stream()
                .map(d -> d.getPersonalNumber()).collect(Collectors.toList());

        cargoesList = assignment.getCargos().stream()
                .map(c -> c.getCargoIdentifier()).collect(Collectors.toList());

        cargoesStatuses = assignment.getCargos().stream()
                .map(c -> ru.tsystems.shalamov.entities.statuses.CargoStatus.valueOf(c.getStatus().value()))
                .collect(Collectors.toList());

//        driverStatus = DriverStatus.PRIMARY;
//        orderIdentifier = "some order";
//        truckRegistrationNumber = "some truck";
//        involvedDrivers.add(personalNumber);
//        cargoes.put("some_cargo", CargoStatus.PREPARED);
//        cargoes.put("some_other_cargo", CargoStatus.SHIPPED);

        return "driver";
    }

    public String swapStatus() {
        // this.personalNumber = personalNumber;
        //getAssignmentInforamation();

        if (driverStatus != DriverStatus.REST) {
            if (driverStatus == DriverStatus.AUXILIARY)
                driverStatus = DriverStatus.PRIMARY;
            else
                driverStatus = DriverStatus.AUXILIARY;
        }

        // todo query WS to change driverStatus

        return "driver";
    }

    public String swapShift() {
        //  this.personalNumber = personalNumber;
        // getAssignmentInforamation();

        if (driverStatus == DriverStatus.REST) {
            driverStatus = DriverStatus.PRIMARY;
        } else {
            driverStatus = DriverStatus.REST;
        }

        // todo query WS to change driverStatus

        return "driver";
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

    public String getTruckRegistrationNumber() {
        return truckRegistrationNumber;
    }

    public void setTruckRegistrationNumber(String truckRegistrationNumber) {
        this.truckRegistrationNumber = truckRegistrationNumber;
    }

    public List<String> getCargoesList() {
        return cargoesList;
    }

    public void setCargoesList(List<String> cargoesList) {
        this.cargoesList = cargoesList;
    }


    public List<CargoStatus> getCargoesStatuses() {
        return cargoesStatuses;
    }

    public void setCargoesStatuses(List<CargoStatus> cargoesStatuses) {
        this.cargoesStatuses = cargoesStatuses;
    }

}
