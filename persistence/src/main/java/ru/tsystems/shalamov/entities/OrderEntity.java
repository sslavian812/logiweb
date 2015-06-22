package ru.tsystems.shalamov.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "orders", schema = "", catalog = "logiweb")
public class OrderEntity {
    private int id;
    private String orderIdentifier;
    private Integer completed;
    private Integer truck;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_identifier")
    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    @Basic
    @Column(name = "completed")
    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    @Basic
    @Column(name = "truck")
    public Integer getTruck() {
        return truck;
    }

    public void setTruck(Integer truck) {
        this.truck = truck;
    }

    @OneToMany(mappedBy="orderEntity",targetEntity=CargoEntity.class,
            fetch=FetchType.EAGER)
    private Collection cargoEntities;

    @ManyToOne(optional=false)
    @JoinColumn(name="truck",referencedColumnName="id")
    private TruckEntity truckEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (completed != null ? !completed.equals(that.completed) : that.completed != null) return false;
        if (orderIdentifier != null ? !orderIdentifier.equals(that.orderIdentifier) : that.orderIdentifier != null)
            return false;
        if (truck != null ? !truck.equals(that.truck) : that.truck != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderIdentifier != null ? orderIdentifier.hashCode() : 0);
        result = 31 * result + (completed != null ? completed.hashCode() : 0);
        result = 31 * result + (truck != null ? truck.hashCode() : 0);
        return result;
    }
}
