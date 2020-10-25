package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;
    private CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
        companyMapper = new CompanyMapper();
    }

    @GetMapping
    public List<CompanyResponse> getAll() {
        List<Company> companies = companyService.getAll();
        return companies.stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest companyRequest) {
        Company company = companyService.create(companyMapper.toEntity(companyRequest));
        return companyMapper.toResponse(company);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable Integer companyId) {
        Company company = companyService.getById(companyId);
        return companyMapper.toResponse(company);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployees(@PathVariable Integer companyId) {
        return companyService.getCompanyEmployees(companyId);
    }

    @PutMapping("/{companyId}")
    public CompanyResponse updateCompany(@PathVariable Integer companyId, @RequestBody CompanyRequest updatedCompany) {
        Company company = companyService.update(companyId, companyMapper.toEntity(updatedCompany));
        return companyMapper.toResponse(company);
    }

    @DeleteMapping("/{companyId}")
    public Company removeCompanyEmployees(@PathVariable Integer companyId) {
        return companyService.remove(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        List<Company> companies = companyService.getByPage(page, pageSize);
        return companies.stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }
}