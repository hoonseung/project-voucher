package com.study.projectvoucher.domain.employee;


import jakarta.persistence.*;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "employee")
@Entity
public class EmployeeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String position;

    private String department;


    @Builder
    public EmployeeEntity(String name, String position, String department) {
        this.name = name;
        this.position = position;
        this.department = department;
    }
}
