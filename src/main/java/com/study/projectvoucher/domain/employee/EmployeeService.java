package com.study.projectvoucher.domain.employee;

import com.study.projectvoucher.model.employee.EmployeeRequest;
import com.study.projectvoucher.model.employee.EmployeeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse getEmployee(Long no){
        var employeePs = employeeRepository.findById(no)
                .orElseThrow(() ->  new IllegalArgumentException("사원을 찾을 수 없음"));
        return new EmployeeResponse(employeePs.getId(), employeePs.getName(), employeePs.getPosition(), employeePs.getDepartment(),
                employeePs.getCreatedAt(), employeePs.getUpdatedAt());
    }


    @Transactional
    public Long createEmployee(EmployeeRequest employeeRequest){
        var employeeEntity = new EmployeeEntity(employeeRequest.name(), employeeRequest.position(), employeeRequest.department());


        var employeePs = employeeRepository.save(employeeEntity);
        return employeePs.getId();
    }

}
