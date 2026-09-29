
package com.example.demo.exception;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.EmployeeController;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    // Test 404 - Resource Not Found
    @Test
    void testResourceNotFoundException() throws Exception {

        when(employeeService.getEmployeeById(999L))
                .thenThrow(new ResourceNotFoundException(
                        "Employee not found"));

        mockMvc.perform(
                get("/api/v1/employees/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Employee not found"))
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/employees/999"));
    }

    // Test 400 - Validation Error
    @Test
    void testValidationException() throws Exception {

        String invalidEmployee = """
                {
                    "name": "",
                    "department": "",
                    "salary": 10000,
                    "email": ""
                }
                """;

        mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEmployee))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"));
    }

    // Test 409 - Duplicate Email
    @Test
    void testDuplicateEmailException() throws Exception {

        Employee employee = new Employee();

        employee.setName("John");
        employee.setDepartment("IT");
        employee.setSalary(50000);
        employee.setEmail("john@test.com");

        when(employeeService.createEmployee(any(Employee.class)))
                .thenThrow(new DuplicateEmailException(
                        "Email already exists"));

        String employeeJson = """
                {
                    "name": "John",
                    "department": "IT",
                    "salary": 50000,
                    "email": "john@test.com"
                }
                """;

        mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message")
                        .value("Email already exists"));
    }

    // Test 422 - Business Rule
    @Test
    void testBusinessRuleException() throws Exception {

        when(employeeService.createEmployee(any(Employee.class)))
                .thenThrow(new BusinessRuleException(
                        "Employee salary must be at least 15000"));

        String employeeJson = """
                {
                    "name": "John",
                    "department": "IT",
                    "salary": 20000,
                    "email": "john@test.com"
                }
                """;

        mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.message")
                        .value("Employee salary must be at least 15000"));
    }

    // Test 500 - Generic Exception
    @Test
    void testGenericException() throws Exception {

        mockMvc.perform(
                get("/api/v1/employees/test-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message")
                        .value("Internal server error"))
                .andExpect(jsonPath("$.path")
                        .value("/api/v1/employees/test-error"));
    }
}

