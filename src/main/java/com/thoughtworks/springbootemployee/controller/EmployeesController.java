package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeeMapper employeeMapper;
    private EmployeeService employeeService;

    public EmployeesController(EmployeeMapper employeeMapper, EmployeeService employeeService) {
        this.employeeMapper = employeeMapper;
        this.employeeService = employeeService;
    }

    //Getting all employees
    @GetMapping
    public List<EmployeeResponse> getAll() {
        List<Employee> employees = employeeService.getAll();
        return employees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    //Add an employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.create(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(employee);
    }

    //Get specific employee
    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployee(@PathVariable Integer employeeId) {
        Employee employee = employeeService.getById(employeeId);
        return employeeMapper.toResponse(employee);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employeeUpdate) {
        Employee employee = employeeService.update(employeeId, employeeMapper.toEntity(employeeUpdate));
        return employeeMapper.toResponse(employee);
    }

    @DeleteMapping("/{employeeId}")
    public void removeEmployee(@PathVariable Integer employeeId) {
        employeeService.remove(employeeId);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam String gender) {
        List<Employee> employees = employeeService.getByGender(gender);
        return employees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        List<Employee> employees = employeeService.getByPage(page, pageSize);
        return employees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }
}
