package org.example.currencycenter.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transaction_history")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TRANSACTION_TYPE type;
    private LocalDateTime date;
    private BigDecimal amount;
    private BigDecimal exchangeRate;
    private BigDecimal exchangedAmount;
    @ManyToOne
    @JoinColumn(name = "currency_code")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    public Transaction() {
    }

    public Transaction(TRANSACTION_TYPE type, LocalDateTime date, BigDecimal amount, BigDecimal exchangeRate, BigDecimal exchangedAmount, Currency currency, Employee employee) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.exchangeRate = exchangeRate;
        this.exchangedAmount = exchangedAmount;
        this.currency = currency;
        this.employee = employee;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TRANSACTION_TYPE getType() {
        return type;
    }

    public void setType(TRANSACTION_TYPE type) {
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangedAmount() {
        return exchangedAmount;
    }

    public void setExchangedAmount(BigDecimal exchangedAmount) {
        this.exchangedAmount = exchangedAmount;
    }

}
