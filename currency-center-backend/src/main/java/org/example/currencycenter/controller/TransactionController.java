package org.example.currencycenter.controller;


import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.currencycenter.dto.*;
import org.example.currencycenter.service.BalanceService;
import org.example.currencycenter.service.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private TransactionService transactionService;


    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;

    }
    @GetMapping("/get")
    public ResponseEntity<List<TransactionDTO>> showAllTransactions(){
        List<TransactionDTO> all = transactionService.listAllTransactions();
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(all);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id){
        TransactionDTO result = transactionService.getTransactionById(id);

        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseNewTransaction> newTransaction(Authentication auth, @Valid @RequestBody RequestNewTransactionPayload body){
        ResponseNewTransaction resp = transactionService.handleNewTransaction(auth,body);

        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(resp);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
        ResponseMessage message = new ResponseMessage(201,"Successfully deleted transaction...");

        return ResponseEntity.status(message.status()).body(message);
    }








}
