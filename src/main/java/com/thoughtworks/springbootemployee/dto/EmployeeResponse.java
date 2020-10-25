package com.thoughtworks.springbootemployee.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

public class EmployeeResponse {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Integer id, String name, int age, String gender, int salary, int companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public void setId(Integer employeeId) {
        this.id = employeeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
