package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        copyProperties(employee, employeeResponse);
        return employeeResponse;
    }

    public Employee toEntity(EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        copyProperties(employeeRequest, employee);
        return employee;
    }
}
