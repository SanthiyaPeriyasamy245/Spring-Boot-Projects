package com.EmployeeManagementSystem.EMS.mapper;

import com.EmployeeManagementSystem.EMS.dto.EmployeeDto;
import com.EmployeeManagementSystem.EMS.entity.Employee;

public class EmployeeMapper {

    public static Employee toEmployee(EmployeeDto employeeDto){
        Employee employee=new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        return employee;
    }
    public static EmployeeDto toEmployeeDto(Employee employee){
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setDepartment(employee.getDepartment());
        return employeeDto;
    }


}
