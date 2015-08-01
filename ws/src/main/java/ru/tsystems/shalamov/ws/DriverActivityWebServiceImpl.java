package ru.tsystems.shalamov.ws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverActivityService;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;

import javax.jws.WebService;
import javax.ws.rs.ServerErrorException;

/**
 * Created by viacheslav on 29.07.2015.
 */
@WebService(endpointInterface = "ru.tsystems.shalamov.ws.DriverActivityWebService")
public class DriverActivityWebServiceImpl implements DriverActivityWebService {

    /**
     * Log4j {@link org.apache.log4j.Logger}  for logging.
     */
    private static final Logger LOG =
            Logger.getLogger(DriverActivityWebServiceImpl.class);

    public static final String UNEXPECTED = "unexpected:";

    @Autowired
    DriverActivityService driverActivityService;

    @Autowired
    DriverAssignmentService driverAssignmentService;
    // todo: null pointer. unable to autowire.


    @Override
    public void shiftBegin(String personalNumber) {
        try {
            driverActivityService.beginShift(personalNumber);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }

    @Override
    public void shiftEnd(String personalNumber) {
        try {
            driverActivityService.endShift(personalNumber);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }

    @Override
    public void driverStatusToRest(String personalNumber) {
        try {
            driverActivityService.driverStatusChanged(personalNumber, DriverStatus.REST);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }

    @Override
    public void driverStatusToPrimary(String personalNumber) {
        try {
            driverActivityService.driverStatusChanged(personalNumber, DriverStatus.PRIMARY);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }

    @Override
    public void driverStatusToAuxiliary(String personalNumber) {
        try {
            driverActivityService.driverStatusChanged(personalNumber, DriverStatus.AUXILIARY);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }

    @Override
    public void cargoStatusChangedToShipped(String cargoIdentifier) {
        try {
            driverActivityService.cargoStatusChanged(cargoIdentifier, CargoStatus.SHIPPED);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }

    @Override
    public void cargoStatusChangedToDelivered(String cargoIdentifier) {
        try {
            driverActivityService.cargoStatusChanged(cargoIdentifier, CargoStatus.DELIVERED);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }

    @Override
    public DriverAssignmentModel getDriverAssignmentInformation(String personalNumber) {
        try {
            return driverAssignmentService.findDriverAssignmentModelByPersonalNumber(personalNumber);
        } catch (ServiceLayerException e) {
            LOG.warn(UNEXPECTED, e);
            throw new ServerErrorException(406, e);
        }
    }
}
