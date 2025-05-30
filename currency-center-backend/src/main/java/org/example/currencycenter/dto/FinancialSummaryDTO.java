package org.example.currencycenter.dto;

import java.math.BigDecimal;

public record FinancialSummaryDTO(BigDecimal fullExchangedAmount,
                                  BigDecimal fullBUYExchangedAmount,
                                  BigDecimal fullSELLExchangedAmount,
                                  BigDecimal averageExchangeAmount
                                  ) {
}
