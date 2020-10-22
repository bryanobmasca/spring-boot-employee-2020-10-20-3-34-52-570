package com.thoughtworks.springbootemployee.integration;

import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void should_get_all_employees_when_get_all() throws Exception {
        //given
        Employee employee = new Employee(1, "Bryan", 2, "male", 20);
        employeeRepository.save(employee);

        //when then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Bryan"))
                .andExpect(jsonPath("$[0].age").value(2))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(20));
    }

    @Test
    public void should_create_employee_when_create_given_employee_request() throws Exception {
        //given
        String empployeeAsJson = "{\n" +
                "    \"id\" : 1,\n" +
                "    \"name\" : \"Watery\",\n" +
                "    \"age\" : 25,\n" +
                "    \"gender\" : \"female\",\n" +
                "    \"salary\" : 9000\n" +
                "}";
        //when then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(empployeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Watery"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(9000));
    }

    @Test
    public void should_return_specific_employee_when_get_by_id_given_employee_id() throws Exception {
        //given
        Employee employee = new Employee(2, "Vance", 25, "male", 40000000);
        Employee createdEmployee = employeeRepository.save(employee);

        //when then
        mockMvc.perform(get("/employees/", createdEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Vance"))
                .andExpect(jsonPath("$[0].age").value(25))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(40000000));
    }

    @Test
    public void should_update_employee_when_update_given_employee() throws Exception {
        //given
        Employee employee = new Employee(1, "Bryan", 2, "male", 20);
        Employee createdEmployee = employeeRepository.save(employee);
        String updatedEmployee = "{\n" +
                "    \"id\" : 1,\n" +
                "    \"name\" : \"Watery\",\n" +
                "    \"age\" : 25,\n" +
                "    \"gender\" : \"female\",\n" +
                "    \"salary\" : 9000\n" +
                "}";

        //when then
        mockMvc.perform(put("/employees/" + createdEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdEmployee.getId()))
                .andExpect(jsonPath("$.name").value("Watery"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(9000));
    }

    @Test
    public void should_delete_employee_when_delete_given_employee_id() throws Exception {
        //given
        Employee employee = new Employee(2, "Vance", 25, "male", 40000000);
        Employee createdEmployee = employeeRepository.save(employee);

        // when
        mockMvc.perform(delete("/employees/" + createdEmployee.getId()))
                .andExpect(status().isOk());

        // then
        assertFalse(employeeRepository.findById(employee.getId()).isPresent());
    }

    @Test
    public void should_return_specific_employee_when_get_by_gender_given_employee_gender() throws Exception {
        //given
        Employee employee1 = new Employee(1, "Bryan", 2, "male", 20);
        Employee employee2 = new Employee(2, "Vance", 25, "male", 40000000);
        Employee employee3 = new Employee(3, "WaterLily", 16, "female", 500);

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        //when then
        mockMvc.perform(get("/employees?gender=female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee3.getId()))
                .andExpect(jsonPath("$[0].name").value(employee3.getName()))
                .andExpect(jsonPath("$[0].age").value(employee3.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee3.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee3.getSalary()));

        mockMvc.perform(get("/employees?gender=male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()))
                .andExpect(jsonPath("$[1].name").value(employee2.getName()))
                .andExpect(jsonPath("$[1].age").value(employee2.getAge()))
                .andExpect(jsonPath("$[1].gender").value(employee2.getGender()))
                .andExpect(jsonPath("$[1].salary").value(employee2.getSalary()));
    }
}