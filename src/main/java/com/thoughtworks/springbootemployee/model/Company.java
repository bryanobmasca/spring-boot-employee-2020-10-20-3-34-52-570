package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String companyName;
    @OneToMany(
            fetch = FetchType.LAZY, cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "company_id")
    private List<Employee> employees;

    public Company(Integer companyId, String companyName, List<Employee> employees) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employees = employees;
    }

    public Company() {

    }

    public Company(String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return getEmployees().size();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
