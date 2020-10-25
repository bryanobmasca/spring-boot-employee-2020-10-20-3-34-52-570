package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CompanyServiceTest {
    @Test
    public void should_return_companies_when_get_all_companies() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        List<Company> expectedCompanies = asList(new Company(), new Company());
        when(repository.findAll()).thenReturn(expectedCompanies);
        CompanyService service = new CompanyService(repository);

        //when
        List<Company> actual = service.getAll();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_create_companies_when_create_given_one_companies() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);
        Company company = new Company(1, "Alibaba",
               asList(new Employee(), new Employee()));
        when(repository.save(company)).thenReturn(company);

        //when
        Company actual = service.create(company);

        //then
        assertEquals(1, actual.getCompanyId());
    }

    @Test
    public void should_return_specific_company_when_get_company_give_company_id() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);
        Company company = new Company(1, "Alibaba",
                asList(new Employee(), new Employee()));
        Integer companyId = company.getCompanyId();
        when(repository.findById(companyId)).thenReturn(java.util.Optional.of(company));

        //when
        Company actual = service.getById(companyId);

        //then
        assertEquals(1, actual.getCompanyId());
    }

    @Test
    void should_return_updated_company_when_update_company_given_company_id_updated_name() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);
        Company company = new Company(1, "Alibaba",
                asList(new Employee(), new Employee()));
        Company updatedCompany = new Company(1, "Alibabas",
                asList(new Employee(), new Employee()));
        Integer companyId = company.getCompanyId();
        when(repository.findById(companyId)).thenReturn(java.util.Optional.of(company));
        when(repository.save(updatedCompany)).thenReturn(updatedCompany);

        //when
        Company actual = service.update(companyId, updatedCompany);

        //then
        assertEquals("Alibabas", actual.getCompanyName());
    }

    @Test
    void should_delete_company_when_delete_company_given_company_id() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);
        Company company = new Company(1, "Alibaba",
                asList(new Employee(), new Employee()));
        Integer companyId = company.getCompanyId();
        repository.deleteById(companyId);
        //when
        Company actualCompany = service.remove(companyId);

        //then
        Mockito.verify(repository, Mockito.times(1)).deleteById(companyId);
        assertEquals(null, actualCompany);
    }

    @Test
    public void should_return_2_company_when_get_by_page_given_2_page_size() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);
        List<Company> returnedCompanies = asList(
                new Company(1, "Alibaba",
                       asList(new Employee(), new Employee())),
                new Company(2, "Alibabas",
                       asList(new Employee(), new Employee())));

        Integer page = 1;
        Integer pageSize = 2;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Company> company = new PageImpl<>(returnedCompanies);
        when(repository.findAll(pageRequest)).thenReturn(company);
        //when
        List<Company> actual = service.getByPage(page, pageSize);
        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_return_all_employee_when_get_employees_given_company_id() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService service = new CompanyService(repository);
        List<Employee> employees = asList(
                new Employee(1, "Justine", 2, "Male", 2000),
                new Employee(2, "Lily", 2, "Female", 2000)
        );
        Company company = new Company(1, "Alibaba",
                employees);
        Integer companyID = company.getCompanyId();

        when(repository.findById(companyID)).thenReturn(java.util.Optional.of(company));
        //when
        List<Employee> actual = service.getCompanyEmployees(companyID);
        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_throw_exception_when_get_given_wrong_id() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(repository);
        Company company = new Company(1, "Alibaba",
                asList(new Employee(), new Employee()));
        Integer companyId = company.getCompanyId();
        when(repository.findById(companyId)).thenReturn(java.util.Optional.empty());
        //when
        Executable executable = () -> companyService.getById(companyId);
        //then
        Exception exception = assertThrows(CompanyNotFoundException.class,executable);
        assertEquals("Company Id not found", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_update_given_wrong_id() {
        //given
        CompanyRepository repository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(repository);
        Company company = new Company(1, "Alibaba",
                asList(new Employee(), new Employee()));
        Company updatedCompany = new Company(2, "Alibabas",
                asList(new Employee(), new Employee()));
        Integer companyId = company.getCompanyId();
        Integer updatedCompanyId = updatedCompany.getCompanyId();
        when(repository.findById(companyId)).thenReturn(java.util.Optional.of(company));
        when(repository.save(updatedCompany)).thenReturn(updatedCompany);
        //when
        Executable executable = () -> companyService.getById(updatedCompanyId);
        //then
        Exception exception = assertThrows(CompanyNotFoundException.class,executable);
        assertEquals("Company Id not found", exception.getMessage());
    }
}