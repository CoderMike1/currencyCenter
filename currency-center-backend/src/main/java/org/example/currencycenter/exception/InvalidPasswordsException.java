package org.example.currencycenter.exception;

public class InvalidPasswordsException extends RuntimeException {
    public InvalidPasswordsException(String message) {
        super(message);
    }
}
