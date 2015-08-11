package ru.tsystems.shalamov.ws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverActivityService;
import ru.tsystems.shalamov.services.api.DriverInfoService;

import javax.jws.WebService;

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
    DriverInfoService driverAssignmentService;


    @Override
    public void shiftBegin(String personalNumber) throws ServiceFault {
        try {
            driverActivityService.beginShift(personalNumber);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to begin shift", e);
            throw new ServiceFault("unable to begin shift", new FaultBean(), e);
        }
    }

    @Override
    public void shiftEnd(String personalNumber) throws ServiceFault {
        try {
            driverActivityService.endShift(personalNumber);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to end shift", e);
            throw new ServiceFault("unable to end shift", new FaultBean(), e);

        }
    }

    @Override
    public void driverStatusToRest(String personalNumber) throws ServiceFault {
        try {
            driverActivityService.driverStatusChanged(personalNumber, DriverStatus.REST);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to set driver status to REST", e);
            throw new ServiceFault("unable to set driver status to REST", new FaultBean(), e);

        }
    }

    @Override
    public void driverStatusToPrimary(String personalNumber) throws ServiceFault {
        try {
            driverActivityService.driverStatusChanged(personalNumber, DriverStatus.PRIMARY);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to set driver Status to PRIMARY", e);
            throw new ServiceFault("unable to set driver Status to PRIMARY", new FaultBean(), e);

        }
    }

    @Override
    public void driverStatusToAuxiliary(String personalNumber) throws ServiceFault {
        try {
            driverActivityService.driverStatusChanged(personalNumber, DriverStatus.AUXILIARY);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to ser driver status to AUXILIARY", e);
            throw new ServiceFault("unable to ser driver status to AUXILIARY", new FaultBean(), e);

        }
    }

    @Override
    public void cargoStatusChangedToShipped(String cargoIdentifier) throws ServiceFault {
        try {
            driverActivityService.cargoStatusChanged(cargoIdentifier, CargoStatus.SHIPPED);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to set cargo status to SHIPPED", e);
            throw new ServiceFault("unable to set cargo status to SHIPPED", new FaultBean(), e);

        }
    }

    @Override
    public void cargoStatusChangedToDelivered(String cargoIdentifier) throws ServiceFault {
        try {
            driverActivityService.cargoStatusChanged(cargoIdentifier, CargoStatus.DELIVERED);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to set cargo status to DELIVERED", e);
            throw new ServiceFault("unable to set cargo status to DELIVERED", new FaultBean(), e);

        }
    }

    @Override
    public DriverAssignmentModel getDriverAssignmentInformation(String personalNumber) throws ServiceFault {
        try {
            return driverAssignmentService.getPossibleInformationForDriver(personalNumber);
        } catch (ServiceLayerException e) {
            LOG.warn("unable to get assignment information for driver", e);
            throw new ServiceFault("unable to get assignment information for driver", new FaultBean(), e);

        }
    }
}
