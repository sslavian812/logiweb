package ru.tsystems.shalamov.dao.impl;


import org.springframework.stereotype.Component;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.entities.DriverStatusEntity;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.DriverStatusEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
@Component
public class DriverStatusDaoImpl extends GenericDaoImpl<DriverStatusEntity> implements DriverStatusDao {
    public DriverStatusDaoImpl(Class<DriverStatusEntity> type) {
        super(type);
    }

    public DriverStatusDaoImpl()
    {super();}
}