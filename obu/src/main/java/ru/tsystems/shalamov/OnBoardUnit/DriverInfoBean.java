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

    DriverStatus driverStatus = DriverStatus.UNASSIGNED;
    String personalNumber;
    String orderIdentifier;
    String truckRegistrationNumber;
    List<String> involvedDrivers = new ArrayList<>();
    List<String> cargoesList = new ArrayList<>();
    List<CargoStatus> cargoesStatuses = new ArrayList<>();
    String failureMessage = "";
    String primaryDriver = "";

//    String firstName = "";
//    String lastName = "";

    final DriverStatus unassigned = DriverStatus.UNASSIGNED;
    final DriverStatus rest = DriverStatus.REST;
    final DriverStatus auxiliary = DriverStatus.AUXILIARY;


    public String getAssignmentInformation() {
        try {
            // when called, PN should be already set.
            if (personalNumber == null || personalNumber.isEmpty())
                return "fail";

            DriverAssignmentModel assignment = client.getDriverAssignmentInformation(personalNumber);

            driverStatus = assignment.getDriverStatus();
            orderIdentifier = assignment.getOrderIdentifier();
            truckRegistrationNumber = assignment.getTruckRegistrationNumber();
            if (assignment.getCoDrivers() != null) {
                involvedDrivers = assignment.getCoDrivers().stream()
                        .map(d -> d.getPersonalNumber()).collect(Collectors.toList());

                assignment.getCoDrivers().forEach(d -> {
                    if (d.getDriverStatus().equals(DriverStatus.PRIMARY)) {
                        primaryDriver = d.getPersonalNumber();
                    }
                });
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
            failureMessage = serviceFault.getMessage();
            return "fail";
        }
    }


    public String swapStatus() {
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
            failureMessage = serviceFault.getMessage();
            return "fail";
        }
    }

    public String swapShift() {
        try {
            if (driverStatus != DriverStatus.UNASSIGNED) {
                if (driverStatus == DriverStatus.REST) {
                    client.shiftBegin(personalNumber);
                } else {
                    client.shiftEnd(personalNumber);
                }
            }
            getAssignmentInformation();
            return "driver";
        } catch (ServiceFault serviceFault) {
            failureMessage = serviceFault.getMessage();
            return "fail";
        }
    }


    public String becomePrimary() {
        try {
            if (driverStatus == DriverStatus.AUXILIARY) {
                if (primaryDriver != null && primaryDriver != "") {
                    client.driverStatusToAuxiliary(primaryDriver);
                }
                client.driverStatusToPrimary(personalNumber);
                primaryDriver = personalNumber;
            }
            getAssignmentInformation();
            return "driver";
        } catch (ServiceFault serviceFault) {
            failureMessage = serviceFault.getMessage();
            return "fail";
        }
    }

    public boolean ifAllCargoesDelivered() {
        if (driverStatus.equals(DriverStatus.UNASSIGNED))
            return false;
        return cargoesStatuses.stream()
                .filter(c -> !c.equals(CargoStatus.DELIVERED))
                .collect(Collectors.toList()).size() == 0;
    }

    public String completeOrder() {
        try {
            if(driverStatus.equals(DriverStatus.AUXILIARY) || driverStatus.equals(DriverStatus.PRIMARY))
                client.completeOrder(orderIdentifier);
            primaryDriver = "";
            return getAssignmentInformation();
        } catch (ServiceFault serviceFault) {
            failureMessage = serviceFault.getMessage();
            return "fail";
        }
    }


    public String switchCargoStatus(String cargoIdentifier) {
        try {
            if (driverStatus.equals(DriverStatus.AUXILIARY)
                    || driverStatus.equals(DriverStatus.PRIMARY)) {
                for (int i = 0; i < cargoesList.size(); ++i) {
                    if (cargoesList.get(i).equals(cargoIdentifier)) {
                        CargoStatus currentStatus = cargoesStatuses.get(i);
                        if (currentStatus.equals(CargoStatus.PREPARED)) {
                            cargoesStatuses.set(i, CargoStatus.SHIPPED);
                            client.cargoStatusChangedToShipped(cargoIdentifier);
                        } else if (currentStatus.equals(CargoStatus.SHIPPED)) {
                            cargoesStatuses.set(i, CargoStatus.DELIVERED);
                            client.cargoStatusChangedToDelivered(cargoIdentifier);
                        } else if (currentStatus.equals(CargoStatus.DELIVERED)) {
                            cargoesStatuses.set(i, CargoStatus.PREPARED);
                            client.cargoStatusChangedToPrepared(cargoIdentifier);
                        }
                    }

                }
            }
            getAssignmentInformation();
            return "driver";
        } catch (ServiceFault serviceFault) {
            failureMessage = serviceFault.getMessage();
            return "fail";
        }
    }


    public CargoStatus getNext(CargoStatus status) {
        if (status.equals(CargoStatus.PREPARED))
            return CargoStatus.SHIPPED;
        else if (status.equals(CargoStatus.SHIPPED))
            return CargoStatus.DELIVERED;
        else
            return CargoStatus.PREPARED;
    }

    public String mapStatusToColor(CargoStatus status) {
        if (status.equals(CargoStatus.PREPARED))
            return "warning";
        else if (status.equals(CargoStatus.SHIPPED))
            return "info";
        else
            return "success";
    }


    public String getShiftChangeText() {
        if (driverStatus.equals(DriverStatus.REST))
            return "go ON shift";
        else if (driverStatus.equals(DriverStatus.UNASSIGNED))
            return "no action available";
        else
            return "go OFF shift";
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

    public String getFailureMessage() {
        return failureMessage;
    }

    public String getPrimaryDriver() {
        return primaryDriver;
    }

    public void setPrimaryDriver(String primaryDriver) {
        this.primaryDriver = primaryDriver;
    }

    public DriverStatus getUnassigned() {
        return unassigned;
    }

    public DriverStatus getRest() {
        return rest;
    }

    public DriverStatus getAuxiliary() {
        return auxiliary;
    }
}
