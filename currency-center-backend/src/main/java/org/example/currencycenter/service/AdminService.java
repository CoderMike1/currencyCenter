package org.example.currencycenter.service;

import org.example.currencycenter.dto.ResponseEmployeeDTO;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private EmployeeRepository employeeRepository;

    public AdminService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Optional<List<ResponseEmployeeDTO>> getEmployees(){
        return employeeRepository.findAllEmployeesIdAndUsername();
    }



}
