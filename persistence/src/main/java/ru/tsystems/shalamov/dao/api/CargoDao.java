package ru.tsystems.shalamov.dao.api;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.entities.CargoEntity;

/**
 * Introduces some domain-specific operations for {@link ru.tsystems.shalamov.entities.CargoEntity}.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public interface CargoDao extends GenericDao<CargoEntity> {
    public CargoEntity findCargoByCargoIdentifier(String cargoIdentifier) throws DataAccessLayerException;
}
