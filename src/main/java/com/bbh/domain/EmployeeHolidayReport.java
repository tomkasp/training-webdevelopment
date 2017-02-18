package com.bbh.domain;

/**
 * @author Tomasz Kasprzycki
 */
public class EmployeeHolidayReport {

    public Integer yearly_allowance;
    public Integer carry_over_days;
    public Integer available_vacations;
    public Integer planned_vacations;

    public Integer getYearly_allowance() {
        return yearly_allowance;
    }

    public EmployeeHolidayReport setYearly_allowance(Integer yearly_allowance) {
        this.yearly_allowance = yearly_allowance;
        return this;
    }

    public Integer getCarry_over_days() {
        return carry_over_days;
    }

    public EmployeeHolidayReport setCarry_over_days(Integer carry_over_days) {
        this.carry_over_days = carry_over_days;
        return this;
    }

    public Integer getAvailable_vacations() {
        return available_vacations;
    }

    public EmployeeHolidayReport setAvailable_vacations(Integer available_vacations) {
        this.available_vacations = available_vacations;
        return this;
    }

    public Integer getPlanned_vacations() {
        return planned_vacations;
    }

    public EmployeeHolidayReport setPlanned_vacations(Integer planned_vacations) {
        this.planned_vacations = planned_vacations;
        return this;
    }
}
