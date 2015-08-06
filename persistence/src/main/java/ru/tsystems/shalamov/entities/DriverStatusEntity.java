package ru.tsystems.shalamov.entities;

import ru.tsystems.shalamov.entities.statuses.DriverStatus;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "driver_statuses", schema = "", catalog = "logiweb")
public class DriverStatusEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "driver_id_for_status")
    private DriverEntity driverEntity;

    @ManyToOne(optional = true)
    @JoinColumn(name = "current_truck", referencedColumnName = "id")
    private TruckEntity truckEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }


    public DriverStatusEntity() {
    }

    public DriverStatusEntity(DriverEntity driver) {
        this.status = DriverStatus.UNASSIGNED;
        this.driverEntity = driver;
        this.truckEntity = null;
        driver.setDriverStatusEntity(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverStatusEntity that = (DriverStatusEntity) o;

        if (id != that.id) return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
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
