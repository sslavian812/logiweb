package ru.tsystems.shalamov.entities;

import java.sql.Timestamp;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ShiftEntity.class)
public abstract class ShiftEntity_ {

	public static volatile SingularAttribute<ShiftEntity, Timestamp> shiftBegin;
	public static volatile SingularAttribute<ShiftEntity, Timestamp> shiftEnd;
	public static volatile SingularAttribute<ShiftEntity, DriverEntity> driverEntity;

}

