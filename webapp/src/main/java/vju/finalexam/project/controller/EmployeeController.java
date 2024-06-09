package vju.finalexam.project.controller;

import org.springframework.web.bind.annotation.*;

import vju.finalexam.project.model.Employee;
import vju.finalexam.project.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends GenericController<Employee> {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    public EmployeeController(EmployeeRepository repo) {
        super(repo);
    }

}