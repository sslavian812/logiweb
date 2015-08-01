package ru.tsystems.shalamov.ws;

import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.services.DriverAssignment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by viacheslav on 29.07.2015.
 */
@WebService
public interface DriverActivityWebService {

    @WebMethod
    void shiftBegin(@WebParam(name = "PersonalNumber") String personalNumber);

    @WebMethod
    void shiftEnd(@WebParam(name = "PersonalNumber") String personalNumber);


    @WebMethod
    void driverStatusToRest(@WebParam(name = "PersonalNumber") String personalNumber);
    @WebMethod
    void driverStatusToPrimary(@WebParam(name = "PersonalNumber") String personalNumber);
    @WebMethod
    void driverStatusToAuxiliary(@WebParam(name = "PersonalNumber") String personalNumber);


    @WebMethod
    void cargoStatusChangedToShipped(@WebParam(name = "CargoIdentifier") String cargoIdentifier);
    @WebMethod
    void cargoStatusChangedToDelivered(@WebParam(name = "CargoIdentifier") String cargoIdentifier);

    @WebMethod
    DriverAssignmentModel getDriverAssignmentInformation(@WebParam(name = "PersonalNumber") String personalNumber);
}
