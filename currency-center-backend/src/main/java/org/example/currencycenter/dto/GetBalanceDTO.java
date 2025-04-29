package org.example.currencycenter.dto;

import org.example.currencycenter.model.Balance;

import java.util.List;

public record GetBalanceDTO(int status, List<Balance> results) {
}
