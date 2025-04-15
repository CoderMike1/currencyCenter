package org.example.currencycenter.exception;

public class InvalidPayload extends RuntimeException {
    public InvalidPayload(String message) {
        super(message);
    }
}
