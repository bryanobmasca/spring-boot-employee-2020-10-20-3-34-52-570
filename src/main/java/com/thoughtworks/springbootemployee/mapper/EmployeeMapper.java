package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setId(employee.getId());
        employeeResponse.setName(employee.getName());
        employeeResponse.setAge(employee.getAge());
        employeeResponse.setGender(employee.getGender());
        employeeResponse.setSalary(employee.getSalary());
        employeeResponse.setCompany_id(employee.getCompany_id());

        return employeeResponse;
    }

    public Employee toEntity(EmployeeRequest employeeRequest){
        Employee employee = new Employee();

        employee.setName(employeeRequest.getName());
        employee.setAge(employeeRequest.getAge());
        employee.setGender(employeeRequest.getGender());
        employee.setSalary(employeeRequest.getSalary());
        employee.setCompany_id(employeeRequest.getCompany_id());

        return employee;
    }
}
