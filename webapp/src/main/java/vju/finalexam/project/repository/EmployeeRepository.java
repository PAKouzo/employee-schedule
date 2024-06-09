package vju.finalexam.project.repository;

import org.springframework.stereotype.Repository;

import vju.finalexam.project.model.Employee;

@Repository
public interface EmployeeRepository extends GenericRepository<Employee> {
    
}