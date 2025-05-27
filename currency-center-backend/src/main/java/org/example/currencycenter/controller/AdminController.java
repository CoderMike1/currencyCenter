package org.example.currencycenter.controller;

import jakarta.validation.Valid;
import org.example.currencycenter.dto.ResponseEmployeeDTO;
import org.example.currencycenter.dto.ResponseMessage;
import org.example.currencycenter.dto.UpdateExchangeRate;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.service.AdminService;
import org.example.currencycenter.service.CurrencyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private CurrencyService currencyService;
    private AdminService adminService;

    public AdminController(CurrencyService currencyService, AdminService adminService){
        this.currencyService = currencyService;
        this.adminService = adminService;
    }

    @GetMapping("/get-employees")
    public ResponseEntity<Optional<List<ResponseEmployeeDTO>>> getEmployees(){
        Optional<List<ResponseEmployeeDTO>> employees = adminService.getEmployees();

        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(employees);
    }

    @GetMapping("/update/nbp/{percent}")
    public ResponseEntity<ResponseMessage> updateBasedOnNBPRates(@PathVariable BigDecimal percent){
        boolean result = currencyService.updateAllPricesBasedOnNBP(percent);
        int status;
        ResponseMessage message;
        if(result){
            status = 201;
            message = new ResponseMessage(status,"successfully updated the rates based on nbp rates");
        }
        else{
            status = 401;
            message = new ResponseMessage(status,"error while updating the rates...");
        }

        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(message);

    }

    @PutMapping("/update/{currency_code}")
    public ResponseEntity<ResponseMessage> updateExchangeRate(@PathVariable String currency_code, @Valid @RequestBody UpdateExchangeRate newRates){

        currencyService.updateRate(currency_code,newRates);
        ResponseMessage message = new ResponseMessage(200,"Successfully updated value...");
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(message);

    }

    @GetMapping("/data/transaction-breakdown")
    public void transactionBreakDown(){

    }


}
