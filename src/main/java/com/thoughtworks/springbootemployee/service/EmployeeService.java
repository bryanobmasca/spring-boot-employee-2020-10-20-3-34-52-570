package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee getById(Integer employeeId) {
        return repository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee Id not found"));
    }

    public Employee update(Integer employeeId, Employee updatedEmployee) {
        Employee employee = repository.findById(employeeId).orElse(null);
        if (employee != null){
            updatedEmployee.setId(employeeId);
            return repository.save(updatedEmployee);
        }
        throw new EmployeeNotFoundException("Employee Id not found");
    }

    public void deleteById(Integer employeeId) {
        Employee employee = repository.findById(employeeId).orElse(null);
        if(employee == null){
            throw new EmployeeNotFoundException("Employee Id not found");
        }
        repository.delete(employee);
    }

    public List<Employee> getByGender(String employeeGender) {
        return repository.findByGender(employeeGender);
    }

    public List<Employee> getByPage(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return repository.findAll(pageRequest).toList();
    }
}
