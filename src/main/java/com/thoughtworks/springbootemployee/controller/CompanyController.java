package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.create(company);
    }

    @GetMapping("/{companyId}")
    public Company getCompany(@PathVariable Integer companyId) {
        return companyService.getById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployees(@PathVariable Integer companyId) {
        return companyService.getCompanyEmployees(companyId);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Integer companyId, @RequestBody Company updatedCompany) {
        return companyService.update(companyId, updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    public Company removeCompanyEmployees(@PathVariable Integer companyId) {
        return companyService.deleteById(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companyService.getByPage(page, pageSize);
    }
}