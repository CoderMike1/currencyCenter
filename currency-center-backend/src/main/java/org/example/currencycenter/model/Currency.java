package org.example.currencycenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Currency {
    @Id
    private String code;
    private String name;
    private double buy_rate;
    private double sell_rate;

    public Currency(){}


    public Currency(String code, String name, double buy_rate, double sell_rate) {
        this.code = code;
        this.name = name;
        this.buy_rate = buy_rate;
        this.sell_rate = sell_rate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuy_rate() {
        return buy_rate;
    }

    public void setBuy_rate(double buy_rate) {
        this.buy_rate = buy_rate;
    }

    public double getSell_rate() {
        return sell_rate;
    }

    public void setSell_rate(double sell_rate) {
        this.sell_rate = sell_rate;
    }
}
