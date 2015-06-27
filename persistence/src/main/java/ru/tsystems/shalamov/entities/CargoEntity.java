package ru.tsystems.shalamov.entities;

import javax.persistence.*;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "cargos", schema = "", catalog = "logiweb")
public class CargoEntity {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
//
//    @Column(name = "order_id")
//    private Integer orderId;

    @Column
    private String denomination;

    @Column
    private Integer weight;

    @Column(nullable = false)
    private int state;


    @ManyToOne(optional=false)
    @JoinColumn(name="order_id",referencedColumnName="id")
    private OrderEntity orderEntity;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

//    public Integer getOrderId() {
//        return orderId;
//    }
//    public void setOrderId(Integer orderId) {
//        this.orderId = orderId;
//    }

    public String getDenomination() {
        return denomination;
    }
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoEntity that = (CargoEntity) o;

        if (id != that.id) return false;
        if (denomination != null ? !denomination.equals(that.denomination) : that.denomination != null) return false;
//        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (state != that.state) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
//        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (denomination != null ? denomination.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (Integer.hashCode(state));
        return result;
    }
}
