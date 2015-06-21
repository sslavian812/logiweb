package ru.tsystems.shalamov.entities;

import javax.persistence.*;

/**
 * Created by viacheslav on 21.06.2015.
 */
@Entity
@Table(name = "trucks", schema = "", catalog = "logiweb")
public class TrucksEntity {
    private int id;
    private Integer crewSize;
    private String registrationNumber;
    private Integer capacity;
    private Integer truckStatusId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "crew_size")
    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    @Basic
    @Column(name = "registration_number")
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Basic
    @Column(name = "capacity")
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "truck_status_id")
    public Integer getTruckStatusId() {
        return truckStatusId;
    }

    public void setTruckStatusId(Integer truckStatusId) {
        this.truckStatusId = truckStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrucksEntity that = (TrucksEntity) o;

        if (id != that.id) return false;
        if (capacity != null ? !capacity.equals(that.capacity) : that.capacity != null) return false;
        if (crewSize != null ? !crewSize.equals(that.crewSize) : that.crewSize != null) return false;
        if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
            return false;
        if (truckStatusId != null ? !truckStatusId.equals(that.truckStatusId) : that.truckStatusId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (crewSize != null ? crewSize.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (truckStatusId != null ? truckStatusId.hashCode() : 0);
        return result;
    }
}
