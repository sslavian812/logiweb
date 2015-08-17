package ru.tsystems.shalamov.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.CargoDao;
import ru.tsystems.shalamov.entities.CargoEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Abstract generic DAO implementation for {@link ru.tsystems.shalamov.entities.CargoEntity}.
 * CRUD operations are inherited. Implements some domain-specific operations.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
@Repository
public class CargoDaoImpl extends GenericDaoImpl<CargoEntity> implements CargoDao {
    private static final Logger LOG = Logger.getLogger(CargoDaoImpl.class);

    public CargoDaoImpl(Class<CargoEntity> type) {
        super(type);
    }

    public CargoDaoImpl() {
        super();
    }


    @Override
    public CargoEntity findCargoByCargoIdentifier(String cargoIdentifier) throws DataAccessLayerException {
        try {
            EntityManager em = getEntityManager();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<CargoEntity> criteriaQuery = criteriaBuilder.createQuery(CargoEntity.class);

            Root<CargoEntity> cargoEntityRoot = criteriaQuery.from(CargoEntity.class);
            return em.createQuery(criteriaQuery.select(cargoEntityRoot).where(criteriaBuilder.equal(
                    cargoEntityRoot.get("cargoIdentifier"), cargoIdentifier))).getSingleResult();
        } catch (NoResultException e) {
            LOG.debug(new Exception("no objects found with cargo identifier [" + cargoIdentifier + "]. null returned.", e));
            return null;
        } catch (Exception e) {
            throw new DataAccessLayerException(e);
        }
    }


}