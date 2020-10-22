package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepositoryLegacy {
    private List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return companies;
    }

    public Company save(Company company) {
        companies.add(company);
        return company;
    }

    public Company getById(Integer companyId) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    public Company update(Integer companyId, Company updatedCompany) {
        if (companies.stream()
                .anyMatch(company -> company.getCompanyId().equals(companyId))) {
            companies.stream()
                    .filter(company -> company.getCompanyId().equals(companyId))
                    .findFirst()
                    .ifPresent(company -> {
                        companies.remove(company);
                        companies.add(updatedCompany);
                    });
            return updatedCompany;
        }
        return null;
    }

    public Company remove(Integer companyId) {
        companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .ifPresent(company -> {
                    company.getEmployees().clear();
                });
        return companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    public List<Company> getByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip(pageSize * page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getCompanyEmployees(Integer companyId) {
        return companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null).getEmployees();
    }
}
