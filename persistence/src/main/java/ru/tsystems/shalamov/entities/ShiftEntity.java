package ru.tsystems.shalamov.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by viacheslav on 26.06.2015.
 */
@Entity
@Table(name = "shifts", schema = "", catalog = "logiweb")
public class ShiftEntity {
    private int id;
    private Timestamp shiftBegin;
    private Timestamp shiftEnd;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shift_begin")
    public Timestamp getShiftBegin() {
        return shiftBegin;
    }

    public void setShiftBegin(Timestamp shiftBegin) {
        this.shiftBegin = shiftBegin;
    }

    @Basic
    @Column(name = "shift_end")
    public Timestamp getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(Timestamp shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    @ManyToOne(optional=false)
    @JoinColumn(name="driver_id",referencedColumnName="id")
    private DriverEntity driverEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShiftEntity that = (ShiftEntity) o;

        if (id != that.id) return false;
        if (shiftBegin != null ? !shiftBegin.equals(that.shiftBegin) : that.shiftBegin != null) return false;
        if (shiftEnd != null ? !shiftEnd.equals(that.shiftEnd) : that.shiftEnd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (shiftBegin != null ? shiftBegin.hashCode() : 0);
        result = 31 * result + (shiftEnd != null ? shiftEnd.hashCode() : 0);
        return result;
    }
}
