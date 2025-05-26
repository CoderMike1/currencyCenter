package org.example.currencycenter.controller;


import jakarta.validation.Valid;
import org.example.currencycenter.dto.AuthenticationResponse;
import org.example.currencycenter.dto.RequestChangePassword;
import org.example.currencycenter.dto.RequestEmployeePayload;
import org.example.currencycenter.dto.ResponseMessage;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/change-password")
    public ResponseEntity<ResponseMessage> changePassword(Authentication auth, @Valid @RequestBody RequestChangePassword request){
        boolean result =  authService.changePassword(auth,request);
        if(result){
            ResponseMessage message = new ResponseMessage(201,"Successfully changed password.");
            return ResponseEntity.status(message.status()).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        else{
            ResponseMessage message = new ResponseMessage(404,"Error while changing password.");
            return ResponseEntity.status(message.status()).contentType(MediaType.APPLICATION_JSON).body(message);
        }
    }


    @GetMapping("/testing")
    public String testowanko(){
        return "gitara";
    }





}
