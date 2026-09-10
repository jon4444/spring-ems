package org.springframework.boot.ems.controller;

import jakarta.validation.Valid;
import org.springframework.boot.ems.dto.EmployeeRequest;
import org.springframework.boot.ems.dto.EmployeeResponse;
import org.springframework.boot.ems.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable("id") Long employeeId) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(employeeId)
        );
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(request));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable("id") Long employeeId,
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(
                        employeeId,
                        request
                )
        );
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable("id") Long employeeId) {

        employeeService.deleteEmployee(employeeId);

        return ResponseEntity.noContent().build();
    }
}