package com.study.projectvoucher.domain.model.employee;

public record EmployeeRequest(
        String name,
        String position,
        String department
) {
}
