package org.example.currencycenter.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateExchangeRate(@Positive BigDecimal buy_rate, @Positive BigDecimal sell_rate) {
}
