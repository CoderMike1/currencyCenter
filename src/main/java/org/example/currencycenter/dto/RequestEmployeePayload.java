package org.example.currencycenter.dto;

import jakarta.validation.constraints.NotNull;

public record RequestEmployeePayload(@NotNull(message = "username field can not be empty") String username,@NotNull(message="password field can not be empty") String password) {
}
