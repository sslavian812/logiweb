package ru.tsystems.shalamov.onBoardUnit;

import ru.tsystems.shalamov.ws.*;

import javax.ejb.EJB;
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

    @EJB
    DriverActivityWebServiceImplService webService;

    @javax.xml.ws.WebServiceRef(DriverActivityWebServiceImplService.class)
    DriverActivityWebService client;

    DriverStatus driverStatus;
    String personalNumber;
    String orderIdentifier;
    String truckRegistrationNumber;
    List<String> involvedDrivers = new ArrayList<>();
    List<String> cargoesList = new ArrayList<>();
    List<CargoStatus> cargoesStatuses = new ArrayList<>();


    public String getAssignmentInformation() {
        try {
            // when called, PN should be already set.
            if (personalNumber == null || personalNumber.isEmpty())
                return "fail";

//        DriverActivityWebService client = webService.getDriverActivityWebServiceImplPort();
//        DriverAssignmentModel assignment = client.getDriverAssignmentInformation(personalNumber);
            DriverAssignmentModel assignment = client.getDriverAssignmentInformation(personalNumber);

            driverStatus = assignment.getDriverStatus();
            orderIdentifier = assignment.getOrderIdentifier();
            truckRegistrationNumber = assignment.getTruckRegistrationNumber();
            if (assignment.getCoDrivers() != null) {
                involvedDrivers = assignment.getCoDrivers().stream()
                        .map(d -> d.getPersonalNumber()).collect(Collectors.toList());
            }

            if (assignment.getCargoes() != null) {
                cargoesList = assignment.getCargoes().stream()
                        .map(c -> c.getCargoIdentifier()).collect(Collectors.toList());

                cargoesStatuses = assignment.getCargoes().stream()
                        .map(c -> c.getStatus())
                        .collect(Collectors.toList());
            }
            return "driver";
        } catch (ServiceFault serviceFault) {
            return "fail";
        }
    }


    public String swapStatus() {
//        DriverActivityWebService client = webService.getDriverActivityWebServiceImplPort();

        try {
            if (driverStatus != DriverStatus.REST && driverStatus != DriverStatus.UNASSIGNED) {
                if (driverStatus == DriverStatus.AUXILIARY) {
                    client.driverStatusToPrimary(personalNumber);
                } else {
                    client.driverStatusToAuxiliary(personalNumber);
                }
                getAssignmentInformation();
            }

            return "driver";
        } catch (ServiceFault serviceFault) {
            return "fail";
        }
    }

    public String swapShift() {
//        DriverActivityWebService client = webService.getDriverActivityWebServiceImplPort();


        try {
            if(driverStatus != DriverStatus.UNASSIGNED) {
                if (driverStatus == DriverStatus.REST) {
                    client.shiftBegin(personalNumber);
                } else {
                    client.shiftEnd(personalNumber);
                }
            }

            getAssignmentInformation();

            return "driver";
        } catch (ServiceFault serviceFault) {
            return "fail";
        }
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
