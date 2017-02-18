package com.bbh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.                                                        
 * 
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The firstname attribute.                                                
     * 
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private ZonedDateTime hireDate;

    @Column(name = "carry_over_days")
    private Integer carryOverDays;

    @Column(name = "yearly_allowence")
    private Integer yearlyAllowence;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Set<DaysOff> daysOffs = new HashSet<>();

    @ManyToOne
    private Employee manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getHireDate() {
        return hireDate;
    }

    public Employee hireDate(ZonedDateTime hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(ZonedDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getCarryOverDays() {
        return carryOverDays;
    }

    public Employee carryOverDays(Integer carryOverDays) {
        this.carryOverDays = carryOverDays;
        return this;
    }

    public void setCarryOverDays(Integer carryOverDays) {
        this.carryOverDays = carryOverDays;
    }

    public Integer getYearlyAllowence() {
        return yearlyAllowence;
    }

    public Employee yearlyAllowence(Integer yearlyAllowence) {
        this.yearlyAllowence = yearlyAllowence;
        return this;
    }

    public void setYearlyAllowence(Integer yearlyAllowence) {
        this.yearlyAllowence = yearlyAllowence;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<DaysOff> getDaysOffs() {
        return daysOffs;
    }

    public Employee daysOffs(Set<DaysOff> daysOffs) {
        this.daysOffs = daysOffs;
        return this;
    }

    public Employee addDaysOff(DaysOff daysOff) {
        daysOffs.add(daysOff);
        daysOff.setEmployee(this);
        return this;
    }

    public Employee removeDaysOff(DaysOff daysOff) {
        daysOffs.remove(daysOff);
        daysOff.setEmployee(null);
        return this;
    }

    public void setDaysOffs(Set<DaysOff> daysOffs) {
        this.daysOffs = daysOffs;
    }

    public Employee getManager() {
        return manager;
    }

    public Employee manager(Employee employee) {
        this.manager = employee;
        return this;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if(employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", email='" + email + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", hireDate='" + hireDate + "'" +
            ", carryOverDays='" + carryOverDays + "'" +
            ", yearlyAllowence='" + yearlyAllowence + "'" +
            '}';
    }
}
