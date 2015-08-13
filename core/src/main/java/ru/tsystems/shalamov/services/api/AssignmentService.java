package ru.tsystems.shalamov.services.api;

import ru.tsystems.shalamov.model.AvailableToAssignModel;
import ru.tsystems.shalamov.services.ServiceLayerException;

import javax.transaction.Transactional;

/**
 * Created by viacheslav on 05.08.2015.
 */
public interface AssignmentService {
    /**
     * Finds suitable trucks and drivers to be assigned to given order.
     * @param orderIdentifier String identifier of Order.
     * @return {@link ru.tsystems.shalamov.model.DriverAssignmentModel}
     * with available drivers and trucks.
     * @throws ServiceLayerException if no order with such identifier found or
     * specifier order has UNASSIGNED status.
     */
    @Transactional
    AvailableToAssignModel findAvailableToAssign(String orderIdentifier) throws ServiceLayerException;

    /**
     * Assigns chosen drivers and truck to specifier order.
     * @param availableToAssignModel {@link ru.tsystems.shalamov.model.DriverAssignmentModel}
     * with chosen drivers and trucks for particular order.
     * @throws ServiceLayerException if:
     * 1) there is no truck with such registration number
     * 2) truck is already assigned to some other uncompleted order
     * 3) truck has insufficient capacity to handle specifier order
     * 4) truck requires more crew, than provided drivers
     */
    @Transactional
    void assignDriversAndTruckToOrder(AvailableToAssignModel availableToAssignModel) throws ServiceLayerException;
}
