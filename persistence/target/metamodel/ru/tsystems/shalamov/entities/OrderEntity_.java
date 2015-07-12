package ru.tsystems.shalamov.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderEntity.class)
public abstract class OrderEntity_ {

	public static volatile ListAttribute<OrderEntity, CargoEntity> cargoEntities;
	public static volatile SingularAttribute<OrderEntity, String> orderIdentifier;
	public static volatile SingularAttribute<OrderEntity, Integer> id;
	public static volatile SingularAttribute<OrderEntity, TruckEntity> truckEntity;
	public static volatile SingularAttribute<OrderEntity, OrderStatus> status;

}

