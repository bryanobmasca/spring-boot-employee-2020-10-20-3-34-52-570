package com.thoughtworks.springbootemployee.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, int age, String gender, int salary) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
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
}
