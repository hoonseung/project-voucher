package com.study.projectvoucher.web.controller;


import com.study.projectvoucher.domain.employee.EmployeeService;
import com.study.projectvoucher.model.employee.EmployeeRequest;
import com.study.projectvoucher.model.employee.EmployeeResponse;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    @GetMapping("/employee/{no}")
    public EmployeeResponse getEmployee(@PathVariable final Long no){
       return employeeService.getEmployee(no);
    }


    @PostMapping("/employee")
    public Long createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.createEmployee(employeeRequest);
    }

}
