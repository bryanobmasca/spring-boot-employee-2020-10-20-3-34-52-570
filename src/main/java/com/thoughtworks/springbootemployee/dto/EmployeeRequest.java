package com.thoughtworks.springbootemployee.dto;

public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Integer company_id;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, Integer age, String gender, Integer salary, Integer company_id) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.company_id = company_id;
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

    public Integer getCompany_id() {
        return company_id;
    }
}
