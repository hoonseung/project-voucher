package com.study.projectvoucher.model.employee;

public record EmployeeResponse(
        Long no,
        String name,
        String position,
        String department
) {
}
