package org.example.currencycenter.dto;
import org.example.currencycenter.model.TRANSACTION_TYPE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record EmployeeDTO(Long id, String username){}

public record TransactionDTO(
        Long id,
        LocalDateTime date,
        TRANSACTION_TYPE type,
        String currency,
        BigDecimal amount,
        BigDecimal exchange_rate,
        BigDecimal exchanged_amount,
        EmployeeDTO employee
) {
    public TransactionDTO(Long id, LocalDateTime date, TRANSACTION_TYPE type, String currency, BigDecimal amount, BigDecimal exchange_rate, BigDecimal exchanged_amount, Long employee_id, String employee_username) {
        this(id,date,type,currency,amount,exchange_rate,exchanged_amount,new EmployeeDTO(employee_id,employee_username));
    }
}
