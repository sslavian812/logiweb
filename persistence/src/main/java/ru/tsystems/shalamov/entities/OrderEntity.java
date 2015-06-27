package ru.tsystems.shalamov.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "orders", schema = "", catalog = "logiweb")
public class OrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "order_identifier", nullable = false)
    private String orderIdentifier;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderEntity", targetEntity = CargoEntity.class,
            fetch = FetchType.EAGER)
    private Collection<CargoEntity> cargoEntities;

    @ManyToOne(optional = true)
    @JoinColumn(name = "truck", referencedColumnName = "id")
    private TruckEntity truckEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus completed) {
        this.status = completed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (orderIdentifier != null ? !orderIdentifier.equals(that.orderIdentifier) : that.orderIdentifier != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderIdentifier != null ? orderIdentifier.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public TruckEntity getTruckEntity() {
        return truckEntity;
    }

    public void setTruckEntity(TruckEntity truckEntity) {
        this.truckEntity = truckEntity;
    }
}
