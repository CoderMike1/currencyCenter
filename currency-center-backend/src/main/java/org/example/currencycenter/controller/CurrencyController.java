package org.example.currencycenter.controller;


import jakarta.validation.Valid;
import org.example.currencycenter.dto.GetBalanceDTO;
import org.example.currencycenter.dto.ResponseMessage;
import org.example.currencycenter.dto.UpdateExchangeRate;
import org.example.currencycenter.exception.CurrencyNotFoundException;
import org.example.currencycenter.model.Currency;
import org.example.currencycenter.service.BalanceService;
import org.example.currencycenter.service.CurrencyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CurrencyController {

    private CurrencyService currencyService;
    private BalanceService balanceService;


    public CurrencyController(CurrencyService currencyService,BalanceService balanceService){
        this.currencyService = currencyService;
        this.balanceService = balanceService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Currency>> getAllCodes(){
        List<Currency> array = currencyService.getAllCurrenciesRates();
        if(array.isEmpty()){
            throw new CurrencyNotFoundException("Array is empty.");
        }
        else{
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(array);
        }

    }

    @GetMapping("/get/{currency_code}")
    public ResponseEntity<Currency> getExchangeRate(@PathVariable String currency_code){
        Currency c = currencyService.getCode(currency_code);

        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(c);
    }

    @GetMapping("/update-nbp/{percent}")
    public ResponseEntity<ResponseMessage> updateBasedOnNBPRates(@PathVariable int percent){
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
    public ResponseEntity<ResponseMessage> setExchangeRate(@PathVariable String currency_code, @Valid @RequestBody UpdateExchangeRate newRates){

        currencyService.updateRates(currency_code,newRates);
        ResponseMessage message = new ResponseMessage(200,"Successfully updated value...");
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(message);

    }
    @GetMapping("/get-balance")
    public ResponseEntity<GetBalanceDTO> getBalance(){

        GetBalanceDTO resp = new GetBalanceDTO(201,balanceService.getBalanceList());

        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(resp);
    }





}
