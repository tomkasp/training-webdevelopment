package com.bbh.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the DaysOff entity.
 */
public class DaysOffDTO implements Serializable {

    private Long id;

    private ZonedDateTime dayOffDate;


    private Long typeId;
    
    private Long employeeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDayOffDate() {
        return dayOffDate;
    }

    public void setDayOffDate(ZonedDateTime dayOffDate) {
        this.dayOffDate = dayOffDate;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long dayOffTypeId) {
        this.typeId = dayOffTypeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DaysOffDTO daysOffDTO = (DaysOffDTO) o;

        if ( ! Objects.equals(id, daysOffDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DaysOffDTO{" +
            "id=" + id +
            ", dayOffDate='" + dayOffDate + "'" +
            '}';
    }
}
