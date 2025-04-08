package org.example.currencycenter.model;

import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class History {

    private LocalDateTime date;
    private double amount;
    private double exchangeRate;
    private double exchangedAmount;

    @ManyToOne
    private Currency currency;

    public History(LocalDateTime date, double amount, double exchangeRate, double exchangedAmount, Currency currency) {
        this.date = date;
        this.amount = amount;
        this.exchangeRate = exchangeRate;
        this.exchangedAmount = exchangedAmount;
        this.currency = currency;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
