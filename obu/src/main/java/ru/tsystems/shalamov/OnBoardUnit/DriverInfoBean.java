package ru.tsystems.shalamov.onBoardUnit;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.model.CargoModel;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.ws.DriverActivityWebService;
import ru.tsystems.shalamov.ws.DriverActivityWebServiceImplService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 25.07.2015.
 */
@ManagedBean
@SessionScoped
public class DriverInfoBean {

    @Autowired
    DriverActivityWebServiceImplService webService; // TODO make proper DI

    DriverStatus driverStatus;
    String personalNumber;
    String orderIdentifier;
    String truckRegistrationNumber;
    List<String> involvedDrivers = new ArrayList<>();
    List<String> cargoesList = new ArrayList<>();
    List<CargoStatus> cargoesStatuses = new ArrayList<>();


    public String getAssignmentInformation() {
        // when called, PN should be already set.
        if (personalNumber == null || personalNumber.isEmpty())
            return "fail";

        webService = new DriverActivityWebServiceImplService();
        DriverActivityWebService client = webService.getDriverActivityWebServiceImplPort();
        ru.tsystems.shalamov.ws.DriverAssignmentModel serialized = client.getDriverAssignmentInformation(personalNumber);

        // todo is there a better way to convert generated data to my models??
        DriverAssignmentModel assignment = new DriverAssignmentModel();

        // converting to my model class:
        assignment.setDriverPersonalNumber(serialized.getDriverPersonalNumber());
        assignment.setTruckRegistrationNumber(serialized.getTruckRegistrationNumber());
        assignment.setOrderIdentifier(serialized.getOrderIdentifier());
        assignment.setDriverStatus(ru.tsystems.shalamov.entities.statuses.DriverStatus.valueOf(
                serialized.getDriverStatus().value()));
        assignment.setCoDrivers(serialized.getCoDrivers().stream()
                .map(d -> new DriverModel(d.getFirstName(),
                        d.getLastName(),
                        d.getPersonalNumber(),
                        DriverStatus.valueOf(d.getDriverStatus().value()),
                        d.getTruckRegistrationNumber()))
                .collect(Collectors.toList()));
        assignment.setCargoes(serialized.getCargoes().stream()
                .map(c -> new CargoModel(c.getCargoIdentifier(),
                        c.getDenomination(),
                        c.getWeight(),
                        CargoStatus.valueOf(c.getStatus().value()),
                        c.getOrderIdentifier()))
                .collect(Collectors.toList()));


        driverStatus = assignment.getDriverStatus();
        orderIdentifier = assignment.getOrderIdentifier();
        truckRegistrationNumber = assignment.getTruckRegistrationNumber();
        involvedDrivers = assignment.getCoDrivers().stream()
                .map(d -> d.getPersonalNumber()).collect(Collectors.toList());

        cargoesList = assignment.getCargoes().stream()
                .map(c -> c.getCargoIdentifier()).collect(Collectors.toList());

        cargoesStatuses = assignment.getCargoes().stream()
                .map(c -> c.getStatus())
                .collect(Collectors.toList());

        return "driver";
    }

    public String swapStatus() {
        DriverActivityWebService client = webService.getDriverActivityWebServiceImplPort();

        if (driverStatus != DriverStatus.REST) {
            if (driverStatus == DriverStatus.AUXILIARY) {
                client.driverStatusToPrimary(personalNumber);
//                driverStatus = DriverStatus.PRIMARY;
            } else {
                client.driverStatusToAuxiliary(personalNumber);
//                driverStatus = DriverStatus.AUXILIARY;
            }
            getAssignmentInformation();
        }

        return "driver";
    }

    public String swapShift() {
        DriverActivityWebService client = webService.getDriverActivityWebServiceImplPort();

        if (driverStatus == DriverStatus.REST) {
            client.shiftBegin(personalNumber);
//            driverStatus = DriverStatus.PRIMARY;
        } else {
            client.shiftEnd(personalNumber);
//            driverStatus = DriverStatus.REST;
        }

        getAssignmentInformation();

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
