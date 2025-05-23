package org.example.currencycenter.dto;


import org.example.currencycenter.model.TRANSACTION_TYPE;

import java.math.BigDecimal;

record NewTransactionPayload(Long id,
                             TRANSACTION_TYPE type,
                             BigDecimal amount,
                             BigDecimal exchangeRate,
                             BigDecimal exchangedAmount,
                             String currency){}



public record ResponseNewTransaction(String message,NewTransactionPayload body) {

    public ResponseNewTransaction(String message, Long id,TRANSACTION_TYPE type, BigDecimal amount, BigDecimal exchangeRate, BigDecimal exchangedAmount, String currency ){
        this(message,new NewTransactionPayload(id,type,amount,exchangeRate,exchangedAmount,currency));
    }


}
