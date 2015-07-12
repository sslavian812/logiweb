package ru.tsystems.shalamov.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CargoEntity.class)
public abstract class CargoEntity_ {

	public static volatile SingularAttribute<CargoEntity, OrderEntity> orderEntity;
	public static volatile SingularAttribute<CargoEntity, Integer> weight;
	public static volatile SingularAttribute<CargoEntity, Integer> id;
	public static volatile SingularAttribute<CargoEntity, String> denomination;
	public static volatile SingularAttribute<CargoEntity, CargoStatus> status;

}

