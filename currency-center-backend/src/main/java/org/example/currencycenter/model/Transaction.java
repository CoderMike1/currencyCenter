package org.example.currencycenter.model;

import jakarta.persistence.*;

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
    private double amount;
    private double exchangeRate;
    private double exchangedAmount;
    @ManyToOne
    @JoinColumn(name = "currency_code")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    public Transaction() {
    }

    public Transaction(TRANSACTION_TYPE type, LocalDateTime date, double amount, double exchangeRate, double exchangedAmount, Currency currency, Employee employee) {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getExchangedAmount() {
        return exchangedAmount;
    }

    public void setExchangedAmount(double exchangedAmount) {
        this.exchangedAmount = exchangedAmount;
    }

}
