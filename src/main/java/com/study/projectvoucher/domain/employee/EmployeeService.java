package com.study.projectvoucher.domain.employee;

import com.study.projectvoucher.domain.model.employee.EmployeeRequest;
import com.study.projectvoucher.domain.model.employee.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final Map<Long, EmployeeResponse> map = new ConcurrentHashMap<>();

    private static final AtomicLong employeeNo = new AtomicLong(1L);


    public EmployeeResponse getEmployee(Long no){
        var employee = map.getOrDefault(no, new EmployeeResponse(no, "익명", "없음", "없음"));
        return new EmployeeResponse(employee.no(), employee.name(), employee.position(), employee.department());
    }


    public Long createEmployee(EmployeeRequest employeeRequest){
        var no = employeeNo.getAndIncrement();
        map.put(no, new EmployeeResponse(no, employeeRequest.name(), employeeRequest.position(), employeeRequest.department()));
        return no;
    }

}
