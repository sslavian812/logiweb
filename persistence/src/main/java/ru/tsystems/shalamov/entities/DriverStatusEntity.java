package ru.tsystems.shalamov.entities;

import javax.persistence.*;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "driver_statuses", schema = "", catalog = "logiweb")
public class DriverStatusEntity {
    private int id;
    private Integer status;
    private Integer currentTruck;
    @OneToOne(optional=false,cascade=CascadeType.ALL,
            mappedBy="driverStatusEntity",targetEntity=DriverEntity.class)
    private DriverEntity driverEntity;
    @ManyToOne(optional=false)
    @JoinColumn(name="current_truck",referencedColumnName="id")
    private TruckEntity truckEntity;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "current_truck")
    public Integer getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Integer currentTruck) {
        this.currentTruck = currentTruck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverStatusEntity that = (DriverStatusEntity) o;

        if (id != that.id) return false;
        if (currentTruck != null ? !currentTruck.equals(that.currentTruck) : that.currentTruck != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (currentTruck != null ? currentTruck.hashCode() : 0);
        return result;
    }
}
