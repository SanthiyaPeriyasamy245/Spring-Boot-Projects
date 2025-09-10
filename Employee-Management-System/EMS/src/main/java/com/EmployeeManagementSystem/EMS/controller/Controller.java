package com.EmployeeManagementSystem.EMS.controller;

import com.EmployeeManagementSystem.EMS.dto.EmployeeDto;
import com.EmployeeManagementSystem.EMS.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/employees")
public class Controller {


    EmployeeService employeeService;

    public Controller(EmployeeService employeeService){
        this.employeeService=employeeService;
    }



    // creating employee record
    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto emp=employeeService.createEmployee(employeeDto);
        return ResponseEntity.ok("Employee created with id: "+emp.getId());
    }



    // getting employee record by id
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable ("id") Long id){
    return ResponseEntity.ok(employeeService.getEmployeeById(id));}


    //getting all employee records
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){

        return ResponseEntity.ok(employeeService.getAllEmployees());
    }




    // updating employee record by id
    @PutMapping("{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable("id") Long id,@RequestBody
    EmployeeDto employeeDto){
        EmployeeDto emp=employeeService.updateEmployee(id,employeeDto);
        return ResponseEntity.ok("Employee updated with id: "+emp.getId());
    }


    // deleting employee record by id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted with id: "+id);

    }


}
