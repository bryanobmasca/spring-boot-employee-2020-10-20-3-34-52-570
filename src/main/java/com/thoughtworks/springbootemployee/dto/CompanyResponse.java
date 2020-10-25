package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.List;

public class CompanyResponse {
    private String companyName;
    private int employeeNumber;
    private List<Employee> employees;

    public CompanyResponse() {
    }

    public CompanyResponse(String companyName, int employeeNumber, List<Employee> employees) {
        this.companyName = companyName;
        this.employeeNumber = employeeNumber;
        this.employees = employees;
    }

    public CompanyResponse(String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public CompanyResponse(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeeNumber() {
        if (getEmployees() != null){
            return getEmployees().size();
        }
        else{
            return 0;
        }
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
