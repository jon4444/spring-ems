package org.springframework.boot.ems.dto;

import org.springframework.boot.ems.entity.Employee;

public class EmployeeResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    public EmployeeResponse(
            Long id,
            String firstname,
            String lastname,
            String email) {

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public static EmployeeResponse fromEmployee(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail()
        );
    }
}