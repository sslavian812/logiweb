package ru.tsystems.shalamov.dao.api;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.entities.OrderEntity;

/**
 * Introduces some domain-specific operations for {@link ru.tsystems.shalamov.entities.OrderEntity}.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface OrderDao extends GenericDao<OrderEntity> {

    OrderEntity findByTruckId(int truckId) throws DataAccessLayerException;

    OrderEntity findByOrderIdentifier(String orderIdentifier) throws DataAccessLayerException;
}
