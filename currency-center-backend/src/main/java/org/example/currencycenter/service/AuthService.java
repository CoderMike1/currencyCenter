package org.example.currencycenter.service;


import org.example.currencycenter.dto.AuthenticationResponse;
import org.example.currencycenter.dto.RequestChangePassword;
import org.example.currencycenter.dto.RequestEmployeePayload;
import org.example.currencycenter.exception.EmployeeAlreadyExistsException;
import org.example.currencycenter.exception.InvalidPasswordsException;
import org.example.currencycenter.exception.InvalidPayload;
import org.example.currencycenter.exception.QueryDBException;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.repository.EmployeeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final EmployeeRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(EmployeeRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RequestEmployeePayload request){

        Optional<Employee> exists = repository.findByUsername(request.username());
        if(exists.isPresent()){
            throw new EmployeeAlreadyExistsException("Employee "+request.username()+" already exists.");
        }

        Employee employee = new Employee();
        employee.setUsername(request.username());
        employee.setPassword(passwordEncoder.encode(request.password()));

        repository.save(employee);
        String token = jwtService.generateToken(employee);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(RequestEmployeePayload request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    ));
        }
        catch(BadCredentialsException e){
            throw new InvalidPasswordsException("incorrect login or password");
        }
           Employee employee = repository.findByUsername(request.username()).orElseThrow();
           String token = jwtService.generateToken(employee);

           return new AuthenticationResponse(token);
    }

    public boolean changePassword(Authentication auth, RequestChangePassword request){
        String old_password = request.old_password();
        String new_password = request.new_password();
        if(old_password.equals(new_password)){
            throw new InvalidPasswordsException("New password and old password are the same.");
        }
        String username = auth.getName();

        Employee employee = repository.findByUsername(username).orElseThrow(()->new QueryDBException("Employee not found"));

        boolean matches = passwordEncoder.matches(old_password, employee.getPassword());
        if(matches){
            employee.setPassword(passwordEncoder.encode(new_password));
            repository.save(employee);
            return true;
        }
        throw new InvalidPasswordsException("Old password is incorrect.");

    }






}
