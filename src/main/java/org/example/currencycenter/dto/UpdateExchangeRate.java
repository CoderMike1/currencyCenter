package org.example.currencycenter.dto;

import jakarta.validation.constraints.Positive;

public record UpdateExchangeRate(@Positive double buy_rate, @Positive double sell_rate) {
}
