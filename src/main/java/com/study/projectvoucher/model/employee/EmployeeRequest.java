package com.study.projectvoucher.model.employee;

public record EmployeeRequest(
        String name,
        String position,
        String department
) {
}
