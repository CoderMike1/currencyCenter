package org.example.currencycenter.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Currency {
    @Id
    private String code;
    private String name;
    @Column(scale=2)
    private BigDecimal buyRate;
    @Column(scale=2)
    private BigDecimal sellRate;

    public Currency(){}

    public Currency(String code, String name, BigDecimal buyRate, BigDecimal sellRate) {
        this.code = code;
        this.name = name;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
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

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }
}
