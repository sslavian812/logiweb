package ru.tsystems.shalamov.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "trucks", schema = "", catalog = "logiweb")
public class TruckEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "crew_size", nullable = false)
    private int crewSize;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "truck_status", nullable = false)
    private int truckStatus;

    @OneToMany(mappedBy = "truckEntity", targetEntity = OrderEntity.class,
            fetch = FetchType.EAGER)
    private Collection orderEntities;

    @OneToMany(mappedBy = "truckEntity", targetEntity = DriverStatusEntity.class,
            fetch = FetchType.EAGER)
    private Collection driverStatusEntities;


    public TruckEntity() {
        this.capacity           = 0;
        this.crewSize           = 0;
        this.truckStatus        = 0;
        this.registrationNumber = "abacaba";
    }

    public TruckEntity(int crewSize, String registrationNumber, int capacity, int truckStatus)
    {
        this.capacity           = capacity          ;
        this.crewSize           = crewSize          ;
        this.truckStatus        = truckStatus       ;
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

    public int getTruckStatus() {
        return truckStatus;
    }

    public void setTruckStatus(int truckStatus) {
        this.truckStatus = truckStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckEntity that = (TruckEntity) o;

        if (id != that.id) return false;
        if (capacity != that.capacity) return false;
        if (crewSize != that.crewSize) return false;
        if (truckStatus != that.truckStatus) return false;
        if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (Integer.hashCode(crewSize));
        result = 31 * result + (Integer.hashCode(capacity));
        result = 31 * result + (Integer.hashCode(truckStatus));
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        return result;
    }
}
