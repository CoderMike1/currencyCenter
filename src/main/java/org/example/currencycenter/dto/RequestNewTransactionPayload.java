package org.example.currencycenter.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.currencycenter.model.TRANSACTION_TYPE;

public record RequestNewTransactionPayload(@NotNull(message = "type must be 'BUY' or 'SELL'") TRANSACTION_TYPE type,
                                           @NotNull @Positive double amount,
                                           @NotNull String currency) {
}
