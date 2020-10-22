package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryLegacy {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll() {
        return employees;
    }

    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee getById(Integer employeeId) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    public Employee update(Integer employeeId, Employee updatedEmployee) {
        employees.stream().filter(employee -> employee.getId().equals(employeeId))
                .findFirst().ifPresent(employee -> {
            employees.remove(employee);
            employees.add(updatedEmployee);
        });
        return updatedEmployee;
    }

    public void remove(Integer employeeId) {
        employees.stream().filter(employee -> employee.getId().equals(employeeId))
                .findFirst().ifPresent(employees::remove);
    }

    public List<Employee> getByGender(String employeeGender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(employeeGender))
                .collect(Collectors.toList());
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        return employees.stream()
                .skip(pageSize * page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
