package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;

    public CompanyService() {
    }

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company getById(Integer companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company Id not found"));
    }

    public Company update(Integer companyId, Company updatedCompany) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            updatedCompany.setId(companyId);
            return companyRepository.save(updatedCompany);
        }
        throw new CompanyNotFoundException("Company Id not found");
    }

    public Company deleteById(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            for (Employee employee : company.getEmployees()) {
                employee.setCompanyId(null);
                employeeRepository.save(employee);
            }
            company.setEmployees(null);
            companyRepository.save(company);
            return company;
        }
        throw new CompanyNotFoundException("Company Id not found");
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return companyRepository.findAll(pageRequest).toList();
    }

    public List<Employee> getCompanyEmployees(Integer companyID) {
        return companyRepository.findById(companyID).map(Company::getEmployees).orElse(null);
    }
}
