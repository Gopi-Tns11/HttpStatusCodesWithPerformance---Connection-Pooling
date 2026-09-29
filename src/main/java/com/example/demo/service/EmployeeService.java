package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

import com.example.demo.exception.BusinessRuleException;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                 new ResourceNotFoundException("Employee not found"));
    }

    // Create employee
//    public Employee createEmployee(Employee employee) {
//
//        return employeeRepository.save(employee);
//    }
    
    public Employee createEmployee(Employee employee) {

        if (employee.getSalary() < 15000) {
            throw new BusinessRuleException(
                    "Employee salary must be at least 15000");
        }

        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
        	throw new DuplicateEmailException("Email already exists");
        }

        return employeeRepository.save(employee);
    }
    // Update employee
    public Employee updateEmployee(Long id, Employee employee) {

        if (employee.getSalary() < 15000) {
            throw new BusinessRuleException(
                    "Employee salary must be at least 15000");
        }

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
          new ResourceNotFoundException("Employee not found"));

        existingEmployee.setName(employee.getName());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setEmail(employee.getEmail());

        return employeeRepository.save(existingEmployee);
    }

    // Delete employee
    public void deleteEmployee(Long id) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Employee not found"));

        employeeRepository.delete(existingEmployee);
    }
}