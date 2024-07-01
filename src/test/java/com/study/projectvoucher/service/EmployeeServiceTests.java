package com.study.projectvoucher.service;


import com.study.projectvoucher.domain.employee.EmployeeService;
import com.study.projectvoucher.model.employee.EmployeeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;


    @Test
    void whenEmployeeCreatedThenEnableRetrieve(){
        var name = "홍길동";
        var position = "사원";
        var department = "개발팀";
        var employee = new EmployeeRequest(name, position, department);


        var employeeNo = employeeService.createEmployee(employee);
        var employeePs = employeeService.getEmployee(employeeNo);

        assertThat(employeePs).isNotNull();
        assertThat(employeeNo).isEqualTo(1L);
        assertThat(employeePs.department()).isEqualTo(department);

    }
}
