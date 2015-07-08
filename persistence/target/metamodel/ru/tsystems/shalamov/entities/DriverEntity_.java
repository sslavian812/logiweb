package ru.tsystems.shalamov.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DriverEntity.class)
public abstract class DriverEntity_ {

	public static volatile SingularAttribute<DriverEntity, String> firstName;
	public static volatile SingularAttribute<DriverEntity, String> lastName;
	public static volatile SingularAttribute<DriverEntity, DriverStatusEntity> driverStatusEntity;
	public static volatile SingularAttribute<DriverEntity, String> personalNumber;

}

