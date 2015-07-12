package ru.tsystems.shalamov.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DriverEntity.class)
public abstract class DriverEntity_ {

	public static volatile SingularAttribute<DriverEntity, String> firstName;
	public static volatile SingularAttribute<DriverEntity, String> lastName;
	public static volatile SingularAttribute<DriverEntity, DriverStatusEntity> driverStatusEntity;
	public static volatile SingularAttribute<DriverEntity, Integer> id;
	public static volatile SingularAttribute<DriverEntity, String> personalNumber;

}

