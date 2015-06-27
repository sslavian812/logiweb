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

    @Column
    private String denomination;

    @Column
    private Integer weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CargoStatus status;


    @ManyToOne(optional=false)
    @JoinColumn(name="order_id",referencedColumnName="id")
    private OrderEntity orderEntity;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


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

    public CargoStatus getStatus() {
        return status;
    }
    public void setStatus(CargoStatus status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoEntity that = (CargoEntity) o;

        if (id != that.id) return false;
        if (denomination != null ? !denomination.equals(that.denomination) : that.denomination != null) return false;
        if (status != that.status) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (denomination != null ? denomination.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
