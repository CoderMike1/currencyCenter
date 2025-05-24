package org.example.currencycenter.service;

import org.example.currencycenter.exception.InsufficientAccountBalance;
import org.example.currencycenter.exception.QueryDBException;
import org.example.currencycenter.model.Balance;
import org.example.currencycenter.repository.BalanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BalanceService {

    private BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository){
        this.balanceRepository = balanceRepository;
    }

    public List<Balance> getBalanceList(){
        List<Balance> balanceList = balanceRepository.findPositiveBalance();

        return balanceList;
    }

    public boolean addAmount(String currencyCode, BigDecimal amount){
        int resp = balanceRepository.addAmount(currencyCode,amount);
        if(resp>0){
            return true;
        }
        else{
            throw new QueryDBException("error while adding amount...");
        }
    }
    public void ifEnoughMoneyOnAccount(String currencyCode, BigDecimal amount){
        BigDecimal currency_balance = balanceRepository.getAmount(currencyCode);
        if(currency_balance.compareTo(amount) < 0){
            throw new InsufficientAccountBalance("Not enough money on "+currencyCode+" wallet.");
        }

    }
    public boolean subtractAmount(String currencyCode, BigDecimal amount){
        int resp = balanceRepository.subtractAmount(currencyCode,amount);
        if(resp>0){
            return true;
        }
        else{
            throw new QueryDBException("error while subtracting amount...");
        }
    }




}
