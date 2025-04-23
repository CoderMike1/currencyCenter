package org.example.currencycenter.dto;


import org.example.currencycenter.model.TRANSACTION_TYPE;

record NewTransactionPayload(Long id,
                             TRANSACTION_TYPE type,
                             double amount,
                             double exchangeRate,
                             double exchangedAmount,
                             String currency){}



public record ResponseNewTransaction(String message,NewTransactionPayload body) {

    public ResponseNewTransaction(String message, Long id,TRANSACTION_TYPE type, double amount, double exchangeRate, double exchangedAmount, String currency ){
        this(message,new NewTransactionPayload(id,type,amount,exchangeRate,exchangedAmount,currency));
    }


}
