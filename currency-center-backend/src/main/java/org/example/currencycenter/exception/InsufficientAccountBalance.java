package org.example.currencycenter.exception;

public class InsufficientAccountBalance extends RuntimeException {
    public InsufficientAccountBalance(String message) {
        super(message);
    }
}
