package org.example.currencycenter.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.currencycenter.model.TRANSACTION_TYPE;

import java.math.BigDecimal;

public record RequestNewTransactionPayload(@NotNull(message = "type must be 'BUY' or 'SELL'") TRANSACTION_TYPE type,
                                           @NotNull @Positive BigDecimal amount,
                                           @NotNull String currency) {
}
