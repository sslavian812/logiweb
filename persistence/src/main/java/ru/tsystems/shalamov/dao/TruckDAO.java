package ru.tsystems.shalamov.dao;

import ru.tsystems.shalamov.entities.TruckEntity;

import java.util.List;

/**
 * Introduces some domain-specific operations for {@link ru.tsystems.shalamov.entities.TruckEntity}.
 * Created by viacheslav on 28.06.2015.
 */
public interface TruckDao extends GenericDao<TruckEntity, Integer> {

    public List<TruckEntity> getAll();

}
