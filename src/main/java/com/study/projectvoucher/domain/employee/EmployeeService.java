package com.study.projectvoucher.domain.employee;

import com.study.projectvoucher.domain.model.employee.EmployeeRequest;
import com.study.projectvoucher.domain.model.employee.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public EmployeeResponse getEmployee(Long no){
        var employeePs = employeeRepository.findById(no)
                .orElseThrow(() ->  new RuntimeException("사원을 찾을 수 없음"));
        return new EmployeeResponse(employeePs.getId(), employeePs.getName(), employeePs.getPosition(), employeePs.getDepartment());
    }


    @Transactional
    public Long createEmployee(EmployeeRequest employeeRequest){
        var employeeEntity = EmployeeEntity.builder()
                .name(employeeRequest.name())
                .position(employeeRequest.position())
                .department(employeeRequest.department())
                .build();

        var employeePs = employeeRepository.save(employeeEntity);
        return employeePs.getId();
    }

}
