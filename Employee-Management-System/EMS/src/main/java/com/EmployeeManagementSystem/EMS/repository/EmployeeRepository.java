package com.EmployeeManagementSystem.EMS.repository;

import com.EmployeeManagementSystem.EMS.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
