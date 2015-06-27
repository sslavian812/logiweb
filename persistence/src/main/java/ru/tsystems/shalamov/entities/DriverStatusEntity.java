package ru.tsystems.shalamov.entities;

import javax.persistence.*;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "driver_statuses", schema = "", catalog = "logiweb")
public class DriverStatusEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int status;

//    @Column(name="current_truck")
//    private Integer currentTruck;

    @OneToOne(optional=true, cascade=CascadeType.ALL,
            mappedBy="driverStatusEntity", targetEntity=DriverEntity.class)
    private DriverEntity driverEntity;

    @ManyToOne(optional=false)
    @JoinColumn(name="current_truck",referencedColumnName="id")
    private TruckEntity truckEntity;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

//    public Integer getCurrentTruck() {
//        return currentTruck;
//    }
//    public void setCurrentTruck(Integer currentTruck) {
//        this.currentTruck = currentTruck;
//    }


    public DriverStatusEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverStatusEntity that = (DriverStatusEntity) o;

        if (id != that.id) return false;
//        if (currentTruck != null ? !currentTruck.equals(that.currentTruck) : that.currentTruck != null) return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (Integer.hashCode(status));
//        result = 31 * result + (currentTruck != null ? currentTruck.hashCode() : 0);
        return result;
    }
}
