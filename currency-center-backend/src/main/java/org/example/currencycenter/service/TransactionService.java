package org.example.currencycenter.service;

import org.example.currencycenter.dto.RequestNewTransactionPayload;
import org.example.currencycenter.dto.ResponseNewTransaction;
import org.example.currencycenter.dto.TransactionDTO;
import org.example.currencycenter.exception.CurrencyNotFoundException;
import org.example.currencycenter.exception.InvalidPayload;
import org.example.currencycenter.exception.TransactionNotFoundException;
import org.example.currencycenter.model.Currency;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.model.TRANSACTION_TYPE;
import org.example.currencycenter.model.Transaction;
import org.example.currencycenter.repository.CurrencyRepository;
import org.example.currencycenter.repository.TransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;
    private final BalanceService balanceService;

    public TransactionService(TransactionRepository transactionRepository, CurrencyRepository currencyRepository,BalanceService balanceService) {
        this.transactionRepository = transactionRepository;
        this.currencyRepository = currencyRepository;
        this.balanceService = balanceService;
    }

    public TransactionDTO getTransactionById(Long id){
        Transaction t = transactionRepository.findById(id).orElseThrow(()-> new TransactionNotFoundException("transaction id "+id+" does not exist."));

        return new TransactionDTO(id,t.getDate(),t.getType(),t.getCurrency().getCode(),t.getAmount(),t.getExchangeRate(),t.getExchangedAmount(),t.getEmployee().getId(),t.getEmployee().getUsername());
    }

    public List<TransactionDTO> listAllTransactions(){
        List<Transaction> allT = transactionRepository.findAll();
        List<TransactionDTO> resp = new LinkedList<>();
        allT.stream().forEach(t -> resp.add(new TransactionDTO(t.getId(),t.getDate(),t.getType(),t.getCurrency().getCode(),t.getAmount(),t.getExchangeRate(),t.getExchangedAmount(),t.getEmployee().getId(),t.getEmployee().getUsername())));
        return resp;
    }

    public void deleteTransaction(Long id){
        boolean transaction = transactionRepository.existsById(id);
        if(transaction){
            transactionRepository.deleteById(id);
        }
        else{
            throw new TransactionNotFoundException("Transaction with id "+id+" not found.");
        }
    }

    public ResponseNewTransaction handleNewTransaction(Authentication auth, RequestNewTransactionPayload body){
        ResponseNewTransaction resp = addNewTransaction(auth, body.currency(),body.type(),body.amount());
        return resp;

    }

    private ResponseNewTransaction addNewTransaction(Authentication auth,String currency,TRANSACTION_TYPE type,double amount){
        Employee employee = (Employee) auth.getPrincipal();
        Currency currency_item = currencyRepository.findById(currency.toUpperCase()).orElseThrow(() -> new CurrencyNotFoundException("currency "+currency.toUpperCase()+" not found."));
        double exchange_rate;
        String currency_to_check;
        if(type == TRANSACTION_TYPE.BUY){
            exchange_rate = currency_item.getBuy_rate();
            currency_to_check = "PLN";
        }
        else {
            exchange_rate = currency_item.getSell_rate();
            currency_to_check = currency;
        }
        double exchanged_amount_raw = exchange_rate * amount;
        double t = exchanged_amount_raw*100;
        int y = (int)t;
        double exchanged_amount = (double) y/100;

        if(type == TRANSACTION_TYPE.BUY){
            balanceService.ifEnoughMoneyOnAccount(currency_to_check,exchanged_amount);
        }
        else {
            balanceService.ifEnoughMoneyOnAccount(currency_to_check,amount);
        }
        Transaction t1 = new Transaction(
                type,
                LocalDateTime.now(),
                amount,
                exchange_rate,
                exchanged_amount,
                currency_item,
                employee
        );
        Transaction i = transactionRepository.save(t1);
        if(i == null){
            throw new InvalidPayload("error while adding new transaction");
        }
        else{
            ResponseNewTransaction resp = new ResponseNewTransaction(
                    "Successfully added new transaction!",
                    i.getId(),
                    type,
                    i.getAmount(),
                    i.getExchangeRate(),
                    i.getExchangedAmount(),
                    i.getCurrency().getCode()
            );

            handleBalance(type,i.getCurrency().getCode(),i.getAmount(),i.getExchangedAmount());

            return resp;
        }


    }
    private void handleBalance(TRANSACTION_TYPE type,String currency, double amount, double exchanged_amount){
        if(type == TRANSACTION_TYPE.BUY){
            balanceService.addAmount(currency,amount);
            balanceService.subtractAmount("PLN",exchanged_amount);
        }
        else{
            balanceService.addAmount("PLN",exchanged_amount);
            balanceService.subtractAmount(currency,amount);
        }
    }


}
