package ru.tsystems.shalamov.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by viacheslav on 22.06.2015.
 */
@Entity
@Table(name = "drivers", schema = "", catalog = "logiweb")
public class DriverEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "personal_number", nullable = false)
    private String personalNumber;

    @OneToOne(optional = true)
    @JoinColumn(name = "id")
    private DriverStatusEntity driverStatusEntity;

    @OneToMany(mappedBy = "driverEntity", targetEntity = ShiftEntity.class,
            fetch = FetchType.EAGER)
    private Collection<ShiftEntity> shiftEntities;


    public DriverEntity() {
        firstName = "";
        lastName = "";
        personalNumber = "";
    }

    public DriverEntity(String firstName, String lastName, String personalNumber)
    {
        this.firstName      = firstName     ;
        this.lastName       = lastName      ;
        this.personalNumber = personalNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverEntity that = (DriverEntity) o;

        if (id != that.id) return false;
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
        return result;
    }
}
