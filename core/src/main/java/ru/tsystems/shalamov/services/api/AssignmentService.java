package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.model.AvailableToAssignModel;
import ru.tsystems.shalamov.services.ServiceLayerException;

import javax.transaction.Transactional;

/**
 * Created by viacheslav on 05.08.2015.
 */
public interface AssignmentService {
    @Transactional
    AvailableToAssignModel findAvailableToAssign(String orderIdentifier) throws ServiceLayerException;

    @Transactional
    void assignDriversAndTruckToOrder(AvailableToAssignModel availableToAssignModel) throws ServiceLayerException;
}
