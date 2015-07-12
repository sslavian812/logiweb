package ru.tsystems.shalamov.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DriverStatusEntity.class)
public abstract class DriverStatusEntity_ {

	public static volatile SingularAttribute<DriverStatusEntity, Integer> id;
	public static volatile SingularAttribute<DriverStatusEntity, TruckEntity> truckEntity;
	public static volatile SingularAttribute<DriverStatusEntity, DriverEntity> driverEntity;
	public static volatile SingularAttribute<DriverStatusEntity, DriverStatus> status;

}

