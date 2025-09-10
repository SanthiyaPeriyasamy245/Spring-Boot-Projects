package com.EmployeeManagementSystem.EMS.service;

import com.EmployeeManagementSystem.EMS.dto.EmployeeDto;

import java.util.List;

public interface EmployeeFunctionalities {

    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long id);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
}
