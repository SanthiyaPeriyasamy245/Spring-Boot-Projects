package com.EmployeeManagementSystem.EMS.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name",nullable = false)
    String firstName;
    @Column(name="last_name")
    String lastName;
    @Column(name="email",nullable = false,unique = true)
    String email;
    @Column(name="department",nullable = false)
    String department;


}
