package org.example.currencycenter.service;

import org.example.currencycenter.model.Employee;
import org.example.currencycenter.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public UserDetailsImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found."));
        return employee;
    }
}
