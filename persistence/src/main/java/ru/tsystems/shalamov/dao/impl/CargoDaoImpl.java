package ru.tsystems.shalamov.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.tsystems.shalamov.dao.api.CargoDao;
import ru.tsystems.shalamov.entities.CargoEntity;

import javax.persistence.EntityManager;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.CargoEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
@Repository
public class CargoDaoImpl extends GenericDaoImpl<CargoEntity> implements CargoDao {

    public CargoDaoImpl(Class<CargoEntity> type) {
        super(type);
    }

    public CargoDaoImpl()
    {super();}

//    public EntityManager getEntityManager() {
//        return super.getEntityManager();
//    }
}