package com.study.projectvoucher.domain.model.employee;

public record EmployeeResponse(
        Long no,
        String name,
        String position,
        String department
) {
}
