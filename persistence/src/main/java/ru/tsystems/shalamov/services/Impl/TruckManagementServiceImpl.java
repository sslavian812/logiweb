package ru.tsystems.shalamov.services.impl;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.ServiceLauerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by viacheslav on 01.07.2015.
 */
public class TruckManagementServiceImpl implements TruckManagementService {
    TruckDao truckDao;

    private EntityManager em;

    private EntityManager getEntityManager() {
        return em;
    }

    public TruckManagementServiceImpl(TruckDao truckDao, EntityManager em) {
        this.truckDao = truckDao;
        this.em = em;
    }


    @Override
    public List<TruckEntity> getAllTrucks() throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin(); // just a lock
            List<TruckEntity> list = truckDao.findAll();
            getEntityManager().getTransaction().commit();
            return list;
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void addTruck(TruckEntity truck) throws ServiceLauerException {
        try {
            if (truckDao.findByRegistrationNumber(truck.getRegistrationNumber()) != null) {
                throw new ServiceLauerException("truck registration number already in use!");
            }
            getEntityManager().getTransaction().begin();
            truckDao.create(truck);
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void updateTruck(TruckEntity truck) throws ServiceLauerException {
        try {
            getEntityManager().getTransaction().begin();
            truckDao.update(truck);
            getEntityManager().getTransaction().commit();
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void deleteTruckByRegistrationNumber(String truckRegistrationNumber)
            throws ServiceLauerException {
        try {
            truckDao.delete(truckDao.findByRegistrationNumber(truckRegistrationNumber));
        } catch (DataAccessLayerException e) {
            throw new ServiceLauerException(e);
        } finally {
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().rollback();
        }
    }
}
