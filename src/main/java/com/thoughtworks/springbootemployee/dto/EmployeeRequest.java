package com.thoughtworks.springbootemployee.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer companyId;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, int age, String gender, int salary, int companyId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getCompanyId() {
        return companyId;
    }
}
