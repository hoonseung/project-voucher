package com.study.projectvoucher.web.controller;


import com.study.projectvoucher.model.employee.EmployeeRequest;
import com.study.projectvoucher.model.employee.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class EmployeeController {

    private Map<Long, EmployeeResponse> map = new HashMap<>();

    private static AtomicLong employeeNo = new AtomicLong(1L);


    @GetMapping("/employee/{no}")
    public EmployeeResponse home(@PathVariable final Long no){
        var employee = map.getOrDefault(no, new EmployeeResponse(no, "익명", "없음", "없음"));
        return new EmployeeResponse(employee.no(), employee.name(), employee.position(), employee.department());
    }


    @PostMapping("/employee")
    public Long create(@RequestBody EmployeeRequest employeeRequest){
        var no = employeeNo.getAndIncrement();
        map.put(no, new EmployeeResponse(no, employeeRequest.name(), employeeRequest.position(), employeeRequest.department()));
        return no;
    }

}
