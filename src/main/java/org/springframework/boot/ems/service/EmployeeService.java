package org.springframework.boot.ems.service;

import org.springframework.boot.ems.dto.EmployeeRequest;
import org.springframework.boot.ems.dto.EmployeeResponse;
import org.springframework.boot.ems.entity.Employee;
import org.springframework.boot.ems.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // GET all employees
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(EmployeeResponse::fromEmployee)
                .toList();
    }

    // GET employee by ID
    public EmployeeResponse getEmployeeById(Long employeeId) {

        Employee employee=  employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee not found: " + employeeId
                        )
                );
        return EmployeeResponse.fromEmployee(employee);
    }

    // CREATE employee
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();

        employee.setFirstname(request.getFirstname());
        employee.setLastname(request.getLastname());
        employee.setEmail(request.getEmail());

        Employee savedEmployee =
                employeeRepository.save(employee);

        return EmployeeResponse.fromEmployee(savedEmployee);
    }

    // UPDATE employee
    public EmployeeResponse updateEmployee(
            Long employeeId,
            EmployeeRequest request) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee not found: " + employeeId
                        )
                );

        employee.setFirstname(request.getFirstname());
        employee.setLastname(request.getLastname());
        employee.setEmail(request.getEmail());

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return EmployeeResponse.fromEmployee(updatedEmployee);
    }

    // DELETE employee
    public void deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee not found: " + employeeId
                        )
                );

        employeeRepository.delete(employee);
    }
}