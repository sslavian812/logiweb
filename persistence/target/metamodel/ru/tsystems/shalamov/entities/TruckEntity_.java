package ru.tsystems.shalamov.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TruckEntity.class)
public abstract class TruckEntity_ {

	public static volatile SingularAttribute<TruckEntity, String> registrationNumber;
	public static volatile SingularAttribute<TruckEntity, Integer> crewSize;
	public static volatile SingularAttribute<TruckEntity, Integer> id;
	public static volatile ListAttribute<TruckEntity, DriverStatusEntity> driverStatusEntities;
	public static volatile ListAttribute<TruckEntity, OrderEntity> orderEntities;
	public static volatile SingularAttribute<TruckEntity, Integer> capacity;
	public static volatile SingularAttribute<TruckEntity, TruckStatus> status;

}

