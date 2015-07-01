package ru.tsystems.shalamov.dao.impl;

import ru.tsystems.shalamov.dao.EntityManagerUtil;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.TruckEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.TruckEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 27.06.2015.
 */
public class TruckDaoImpl extends GenericDaoEntityManagerImpl<TruckEntity> implements TruckDao {

    public TruckDaoImpl() {
        super(TruckEntity.class);
    }

    @Override
    public List<TruckEntity> findAll() {
        return EntityManagerUtil.createEntityManager().createQuery("from TruckEntity").getResultList();
    }

    @Override
    public List<TruckEntity> findAllByCapacity(int minimalCapacity) {
        EntityManager em = EntityManagerUtil.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TruckEntity> query = cb.createQuery(TruckEntity.class);
        Root<TruckEntity> truckRoot = query.from(TruckEntity.class);
        ParameterExpression<Integer> parameter = cb.parameter(Integer.class);
        query.select(truckRoot).where(cb.ge(truckRoot.get("capacity"), parameter));

        return em.createQuery(query.select(truckRoot)).getResultList();
    }
}
