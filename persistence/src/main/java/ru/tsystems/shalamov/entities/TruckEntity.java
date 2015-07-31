package ru.tsystems.shalamov.entities;

import ru.tsystems.shalamov.entities.statuses.TruckStatus;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "trucks", schema = "", catalog = "logiweb")
public class TruckEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "crew_size", nullable = false)
    private int crewSize;

    @Column(name = "registration_number", nullable = false, unique = true)
    @Pattern(regexp = "[a-zA-Z]{2}[0-9]{5}", message = "two letters and 5 digits")
    private String registrationNumber;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TruckStatus status;

    @OneToMany(mappedBy = "truckEntity", targetEntity = OrderEntity.class,
            fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntities;

    @OneToMany(mappedBy = "truckEntity", targetEntity = DriverStatusEntity.class,
            fetch = FetchType.EAGER)
    private List<DriverStatusEntity> driverStatusEntities;


    @PreRemove
    private void preRemove() {
        if (orderEntities != null)
            for (OrderEntity o : orderEntities) {
                o.setTruckEntity(null);
            }
        if (driverStatusEntities != null)
            for (DriverStatusEntity d : driverStatusEntities) {
                d.setTruckEntity(null);
            }
    }

    public TruckEntity() {
    }

    public TruckEntity(int crewSize, String registrationNumber, int capacity, TruckStatus truckStatus) {
        this.capacity = capacity;
        this.crewSize = crewSize;
        this.status = truckStatus;
        this.registrationNumber = registrationNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus truckStatus) {
        this.status = truckStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckEntity that = (TruckEntity) o;

        if (id != that.id) return false;
        if (capacity != that.capacity) return false;
        if (crewSize != that.crewSize) return false;
        if (status != that.status) return false;
        if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (Integer.hashCode(crewSize));
        result = 31 * result + (Integer.hashCode(capacity));
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public void setDriverStatusEntities(List<DriverStatusEntity> driverStatusEntities) {
        this.driverStatusEntities = driverStatusEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }

    public List<DriverStatusEntity> getDriverStatusEntities() {
        return driverStatusEntities;
    }
}
