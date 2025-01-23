package com.example.hotelmanagement.models.exception;

public class NoEmployeeFoundException extends RuntimeException{
    private final Long employeeId;
    public NoEmployeeFoundException(Long employeeId) {
        super("No Employee Found with ID: " + employeeId);
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
}
