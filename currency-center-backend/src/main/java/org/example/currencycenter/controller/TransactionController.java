package org.example.currencycenter.controller;


import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.currencycenter.dto.*;
import org.example.currencycenter.model.Balance;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.model.Transaction;
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
    private BalanceService balanceService;

    public TransactionController(TransactionService transactionService,BalanceService balanceService){
        this.transactionService = transactionService;
        this.balanceService = balanceService;
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

    @GetMapping("/get-balance")
    public ResponseEntity<GetBalanceDTO> getBalance(){

        GetBalanceDTO resp = new GetBalanceDTO(201,balanceService.getBalanceList());

        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(resp);
    }




}
