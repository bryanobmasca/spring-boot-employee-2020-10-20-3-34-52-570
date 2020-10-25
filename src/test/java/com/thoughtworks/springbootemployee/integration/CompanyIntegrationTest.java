package com.thoughtworks.springbootemployee.integration;


import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_get_all() throws Exception {
        //given
        Company company = new Company("OOCL", Collections.EMPTY_LIST);
        companyRepository.save(company);
        //when then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$[0].employees").isEmpty());
    }

    @Test
    public void should_return_company_when_post() throws Exception {
        //given
        String stringAsJson = "{\n" +
                "    \"companyName\" : \"OOCL\",\n" +
                "    \"employees\" : [\n" +
                "        {\n" +
                "            \"name\" : \"test\",\n" +
                "            \"age\" : 23,\n" +
                "            \"gender\" : \"male\",\n" +
                "            \"salary\" : 24555\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        //when then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employees").isNotEmpty())
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("test"))
                .andExpect(jsonPath("$.employees[0].age").value(23))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(24555))
                .andExpect(jsonPath("$.employeeNumber").value(1));
    }

    @Test
    public void should_return_company_when_get_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "OOCL", Collections.EMPTY_LIST);
        Integer companyId = companyRepository.save(company).getId();
        //when then
        mockMvc.perform(get("/companies/", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$[0].employees").isEmpty());
    }

    @Test
    public void should_throw_exception_when_get_given_not_existing_company_id() throws Exception {
        //given
        Company company = new Company(1, "OOCL", Collections.EMPTY_LIST);
        Integer companyId = companyRepository.save(company).getId();
        //when then
        mockMvc.perform(get("/companies/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company Id not found"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));
    }


    @Test
    public void should_return_updated_company_when_put_given_company_id() throws Exception {
        //given
        Company company = new Company("OOCL", Collections.EMPTY_LIST);
        Integer companyId = companyRepository.save(company).getId();

        String stringAsJson = "{\n" +
                "    \"companyName\" : \"A\"\n" +
                "}";
        //when then
        mockMvc.perform(put("/companies/" + companyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("A"));
    }

    @Test
    public void should_throw_exception_when_update_given_not_existing_company_id() throws Exception {
        //given
        Company company = new Company(1, "OOCL", Collections.EMPTY_LIST);
        companyRepository.save(company);
        //when then
        mockMvc.perform(put("/companies/500"))
                .andExpect(status().isBadRequest());
    }
}

