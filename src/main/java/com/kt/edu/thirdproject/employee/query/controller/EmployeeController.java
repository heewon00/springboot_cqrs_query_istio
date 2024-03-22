package com.kt.edu.thirdproject.employee.query.controller;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee", description = "Employee API")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins ="*")
@RequestMapping("/api/v1/")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Cacheable(cacheNames = "employee")
    @GetMapping("/employees")
    @Operation(summary ="임직원 전체 조회",description="임직원 전체를 조회 합니다.")
    public List<EmployeeEntity> getEmployeeList() {
        return this.employeeService.getEmployeeList();
    }

    @Operation(summary ="임직원 단건 조회",description="특정 임직원 단건에 대한 정보 조회 합니다.")
    @GetMapping("/employees/{id}")
    public EmployeeEntity getEmployee(@PathVariable Long id) {
        return this.employeeService.getEmployee(id);
    }

}
