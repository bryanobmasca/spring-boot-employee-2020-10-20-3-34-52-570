package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
        return companyRepository.findById(companyId)
                .map(company -> companyRepository.save(updatedCompany))
                .orElseThrow(() -> new CompanyNotFoundException("Company Id not found"));
    }

    public Company remove(Integer companyId) {
        return companyRepository.findById(companyId)
                .map(company -> {
                    company.setEmployees(asList());
                    companyRepository.save(company);

                    return company;
                })
                .orElseThrow(() -> new CompanyNotFoundException("Company Id not found"));
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return companyRepository.findAll(pageRequest).toList();
    }

    public List<Employee> getCompanyEmployees(Integer companyID) {
        return companyRepository.findById(companyID).map(Company::getEmployees).orElse(null);
    }
}
