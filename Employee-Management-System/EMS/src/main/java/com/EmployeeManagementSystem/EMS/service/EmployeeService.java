package com.EmployeeManagementSystem.EMS.service;

import com.EmployeeManagementSystem.EMS.ExceptionHandling.ResourceNotFoundException;
import com.EmployeeManagementSystem.EMS.dto.EmployeeDto;
import com.EmployeeManagementSystem.EMS.entity.Employee;
import com.EmployeeManagementSystem.EMS.mapper.EmployeeMapper;
import com.EmployeeManagementSystem.EMS.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements EmployeeFunctionalities {

    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee= EmployeeMapper.toEmployee(employeeDto);
        employeeRepository.save(employee);
        EmployeeDto employeeDto1= EmployeeMapper.toEmployeeDto(employee);
        return employeeDto1;

    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee not found with id: "+id));
        EmployeeDto employeeDto=EmployeeMapper.toEmployeeDto(employee);
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(EmployeeMapper::toEmployeeDto).toList();
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee existingEmployee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee not found with id: "+id));
        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setDepartment(employeeDto.getDepartment());
        employeeRepository.save(existingEmployee);
        return EmployeeMapper.toEmployeeDto(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existingEmployee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee not found with id: "+id));
        employeeRepository.delete(existingEmployee);

    }
}
