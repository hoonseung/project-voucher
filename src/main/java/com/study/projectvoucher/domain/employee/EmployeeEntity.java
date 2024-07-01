package com.study.projectvoucher.domain.employee;


import com.study.projectvoucher.domain.BaseEntity;
import jakarta.persistence.*;


@Table(name = "employee")
@Entity
public class EmployeeEntity extends BaseEntity {

    private String name;

    private String position;

    private String department;

    protected EmployeeEntity(){}

    public EmployeeEntity(String name, String position, String department) {
        this.name = name;
        this.position = position;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }
}
