package com.thoughtworks.springbootemployee.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String companyName;
    @OneToMany(
            fetch = FetchType.LAZY
    )
    @JoinColumn(columnDefinition = "company_id")
    private List<Employee> employees;

    public Company(Integer companyId, String companyName, List<Employee> employees) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employees = employees;
    }

    public Company() {

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
}
