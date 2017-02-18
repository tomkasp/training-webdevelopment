package com.bbh.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DaysOff.
 */
@Entity
@Table(name = "days_off")
public class DaysOff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "day_off_date")
    private ZonedDateTime dayOffDate;

    @OneToOne
    @JoinColumn(unique = true)
    private DayOffType type;

    @ManyToOne
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDayOffDate() {
        return dayOffDate;
    }

    public DaysOff dayOffDate(ZonedDateTime dayOffDate) {
        this.dayOffDate = dayOffDate;
        return this;
    }

    public void setDayOffDate(ZonedDateTime dayOffDate) {
        this.dayOffDate = dayOffDate;
    }

    public DayOffType getType() {
        return type;
    }

    public DaysOff type(DayOffType dayOffType) {
        this.type = dayOffType;
        return this;
    }

    public void setType(DayOffType dayOffType) {
        this.type = dayOffType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public DaysOff employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DaysOff daysOff = (DaysOff) o;
        if(daysOff.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, daysOff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DaysOff{" +
            "id=" + id +
            ", dayOffDate='" + dayOffDate + "'" +
            '}';
    }
}
