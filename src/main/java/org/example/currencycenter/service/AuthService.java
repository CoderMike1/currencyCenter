package org.example.currencycenter.service;


import org.example.currencycenter.dto.AuthenticationResponse;
import org.example.currencycenter.dto.RequestEmployeePayload;
import org.example.currencycenter.exception.InvalidPayload;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.repository.EmployeeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            throw new InvalidPayload("incorrect login or password");
        }


           Employee employee = repository.findByUsername(request.username()).orElseThrow();
           String token = jwtService.generateToken(employee);

           return new AuthenticationResponse(token);
    }






}
