package org.example.currencycenter.controller;


import jakarta.validation.Valid;
import org.example.currencycenter.dto.AuthenticationResponse;
import org.example.currencycenter.dto.RequestEmployeePayload;
import org.example.currencycenter.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
           @Valid @RequestBody RequestEmployeePayload data
            ){
        return ResponseEntity.status(201).body(authService.register(data));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody RequestEmployeePayload data
    ){
        return ResponseEntity.status(201).body(authService.authenticate(data));
    }





}
