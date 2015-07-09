package ru.tsystems.shalamov.entities;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;

@StaticMetamodel(OrderEntity.class)
public abstract class OrderEntity_ {

	public static volatile ListAttribute<OrderEntity, CargoEntity> cargoEntities;
	public static volatile SingularAttribute<OrderEntity, String> orderIdentifier;
	public static volatile SingularAttribute<OrderEntity, Integer> id;
	public static volatile SingularAttribute<OrderEntity, TruckEntity> truckEntity;
	public static volatile SingularAttribute<OrderEntity, OrderStatus> status;

}

