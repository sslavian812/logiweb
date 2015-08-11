package ru.tsystems.shalamov.ws;

import ru.tsystems.shalamov.model.DriverAssignmentModel;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by viacheslav on 29.07.2015.
 */
@WebService
public interface DriverActivityWebService {

    @WebMethod
    void shiftBegin(@WebParam(name = "PersonalNumber") String personalNumber) throws ServiceFault;

    @WebMethod
    void shiftEnd(@WebParam(name = "PersonalNumber") String personalNumber) throws ServiceFault;


    @WebMethod
    void driverStatusToRest(@WebParam(name = "PersonalNumber") String personalNumber) throws ServiceFault;

    @WebMethod
    void driverStatusToPrimary(@WebParam(name = "PersonalNumber") String personalNumber) throws ServiceFault;

    @WebMethod
    void driverStatusToAuxiliary(@WebParam(name = "PersonalNumber") String personalNumber) throws ServiceFault;


    @WebMethod
    void cargoStatusChangedToShipped(@WebParam(name = "CargoIdentifier") String cargoIdentifier) throws ServiceFault;

    @WebMethod
    void cargoStatusChangedToDelivered(@WebParam(name = "CargoIdentifier") String cargoIdentifier) throws ServiceFault;

    @WebMethod
    DriverAssignmentModel getDriverAssignmentInformation(@WebParam(name = "PersonalNumber") String personalNumber) throws ServiceFault;
}
