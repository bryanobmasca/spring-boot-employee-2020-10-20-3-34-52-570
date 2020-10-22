package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    @Test
    public void should_return_employees_when_get_all_employee() {
        //given
        EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
        List<Employee> expectedEmployees = asList(new Employee(), new Employee());
        when(repository.findAll()).thenReturn(expectedEmployees);
        EmployeeService service = new EmployeeService(repository);

        //when
        List<Employee> actual = service.getAll();

        //then
        assertEquals(2, actual.size());
    }

    @Test
    public void should_create_employee_when_create_given_one_employee() {
        //given
        EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, "Justine", 2, "Male", 2000);
        when(repository.save(employee)).thenReturn(employee);

        //when
        Employee actual = service.create(employee);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    public void should_return_specific_employee_when_get_employee_give_employee_id() {
        //given
        EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, "Justine", 2, "Male", 2000);
        Integer employeeId = employee.getId();
        when(repository.getById(employeeId)).thenReturn(employee);

        //when
        Employee actual = service.getById(employeeId);

        //then
        assertEquals(1, actual.getId());
    }

    @Test
    void should_return_updated_employee_when_update_employee_given_employee_id_updated_name() {
        //given
        EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, "Justine", 2, "Male", 2000);
        Employee updatedEmployee = new Employee(1, "Bryan", 2, "Male", 2000);
        Integer employeeId = employee.getId();
        when(repository.update(employeeId, updatedEmployee)).thenReturn(updatedEmployee);

        //when
        Employee actual = service.update(employeeId, updatedEmployee);

        //then
        assertEquals("Bryan", actual.getName());
    }

    @Test
    void should_delete_employee_when_delete_employee_given_employee_id() {
        //given
        EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService service = new EmployeeService(repository);
        Employee employee = new Employee(1, "Justine", 2, "Male", 2000);
        Integer employeeId = employee.getId();

        //when
        service.remove(employeeId);

        //then
        Mockito.verify(repository, Mockito.times(1)).remove(employeeId);
    }

    @Test
    public void should_return_employees_when_get_employee_by_gender_given_employee_gender() {
        //given
        EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService service = new EmployeeService(repository);
        List<Employee> returnedEmployees = asList(
                new Employee(1, "Justine", 2, "Male", 2000));

        Employee employee = new Employee(1, "Justine", 2, "Male", 2000);
        Employee employee2 = new Employee(2, "Lily", 2, "Female", 2000);
        when(repository.save(employee)).thenReturn(employee);
        when(repository.save(employee)).thenReturn(employee2);
        String employeeGender = "male";
        when(repository.getByGender(employeeGender)).thenReturn(returnedEmployees);

        //when
        List<Employee> actual = service.getByGender(employeeGender);

        //then
        assertSame(returnedEmployees, actual);
    }

    @Test
    public void should_return_2_employee_when_get_by_page_given_2_page_size() {
        //given
        EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
        EmployeeService service = new EmployeeService(repository);
        List<Employee> returnedEmployees = asList(
                new Employee(1, "Justine", 2, "Male", 2000),
                new Employee(2, "Lily", 2, "Female", 2000));

        Integer page = 1;
        Integer pageSize = 2;
        when(repository.getByPage(page, pageSize)).thenReturn(returnedEmployees);
        //when
        List<Employee> actual = service.getByPage(page, pageSize);
        //then
        assertEquals(2, actual.size());
    }
}