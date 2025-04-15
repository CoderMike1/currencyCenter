package org.example.currencycenter.controller;


import jakarta.validation.Valid;
import org.example.currencycenter.dto.ResponseMessage;
import org.example.currencycenter.dto.UpdateExchangeRate;
import org.example.currencycenter.exception.CurrencyNotFoundException;
import org.example.currencycenter.model.Currency;
import org.example.currencycenter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Currency>> getAllCodes(Authentication auth){
        System.out.println(auth);
        List<Currency> array = currencyService.getAllCurrenciesRates();
        if(array.isEmpty()){
            throw new CurrencyNotFoundException("Array is empty.");
        }
        else{
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(array);
        }

    }

    @GetMapping("/update-nbp/{percent}")
    public ResponseEntity<ResponseMessage> updateBasedOnNBPRates(@PathVariable int percent){
        boolean result = currencyService.updateAllPricesBasedOnNBP(percent);

        ResponseMessage message = new ResponseMessage(201,"successfully updated the rates based on nbp rates");

        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(message);


    }


    @GetMapping("/get/{currency_code}")
    public ResponseEntity<Currency> getExchangeRate(@PathVariable String currency_code){
        Currency c = currencyService.getCode(currency_code);

        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(c);
    }

    @PutMapping("/update/{currency_code}")
    public ResponseEntity<ResponseMessage> setExchangeRate(@PathVariable String currency_code, @Valid @RequestBody UpdateExchangeRate newRates){

        currencyService.updateRates(currency_code,newRates);
        ResponseMessage message = new ResponseMessage(200,"Successfully updated value...");
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(message);

    }




}
