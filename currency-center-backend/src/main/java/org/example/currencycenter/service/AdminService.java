package org.example.currencycenter.service;

import org.example.currencycenter.model.Employee;
import org.example.currencycenter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private EmployeeRepository employeeRepository;

    public AdminService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }



}
