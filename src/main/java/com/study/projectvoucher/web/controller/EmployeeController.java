package com.study.projectvoucher.web.controller;


import com.study.projectvoucher.domain.employee.EmployeeService;
import com.study.projectvoucher.domain.model.employee.EmployeeRequest;
import com.study.projectvoucher.domain.model.employee.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/employee/{no}")
    public EmployeeResponse getEmployee(@PathVariable final Long no){
       return employeeService.getEmployee(no);
    }


    @PostMapping("/employee")
    public Long createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.createEmployee(employeeRequest);
    }

}
