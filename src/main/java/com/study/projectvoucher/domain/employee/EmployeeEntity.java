package com.study.projectvoucher.domain.employee;


import com.study.projectvoucher.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "employee")
@Entity
public class EmployeeEntity extends BaseEntity {

    private String name;

    private String position;

    private String department;



}
