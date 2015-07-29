package ru.tsystems.shalamov.ws;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.api.DriverActivityService;

import javax.jws.WebService;

/**
 * Created by viacheslav on 29.07.2015.
 */
@WebService(endpointInterface = "ru.tsystems.shalamov.ws.DriverActivityWebService")
public class DriverActivityWebServiceImpl implements DriverActivityWebService {

    @Autowired
    DriverActivityService driverActivityService;


    @Override
    public void shiftBegin(String personalNumber) {

    }

    @Override
    public void shiftEnd(String personalNumber) {

    }

    @Override
    public void driverStatusToRest(String personalNumber) {

    }

    @Override
    public void driverStatusToPrimary(String personalNumber) {

    }

    @Override
    public void driverStatusToAuxiliary(String personalNumber) {

    }

    @Override
    public void cargoStatusChangedToShipped(String cargoIdentifier) {

    }

    @Override
    public void cargoStatusChangedToDelivered(String cargoIdentifier) {

    }

    @Override
    public DriverAssignment driverAssignmentInformation(String personalNumber) {
        return null;
    }
}
