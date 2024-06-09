package vju.finalexam.project.controller;

import org.springframework.web.bind.annotation.*;

import vju.finalexam.project.model.Customer;
import vju.finalexam.project.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends GenericController<Customer> {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    
    public CustomerController(CustomerRepository repo) {
        super(repo);
    }

}