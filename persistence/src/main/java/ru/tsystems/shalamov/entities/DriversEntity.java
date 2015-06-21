package ru.tsystems.shalamov.entities;

import javax.persistence.*;

/**
 * Created by viacheslav on 21.06.2015.
 */
@Entity
@Table(name = "drivers", schema = "", catalog = "logiweb")
public class DriversEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String personalNumber;
    private Integer driverStatusId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "personal_number")
    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    @Basic
    @Column(name = "driver_status_id")
    public Integer getDriverStatusId() {
        return driverStatusId;
    }

    public void setDriverStatusId(Integer driverStatusId) {
        this.driverStatusId = driverStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriversEntity that = (DriversEntity) o;

        if (id != that.id) return false;
        if (driverStatusId != null ? !driverStatusId.equals(that.driverStatusId) : that.driverStatusId != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (personalNumber != null ? !personalNumber.equals(that.personalNumber) : that.personalNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (personalNumber != null ? personalNumber.hashCode() : 0);
        result = 31 * result + (driverStatusId != null ? driverStatusId.hashCode() : 0);
        return result;
    }
}
