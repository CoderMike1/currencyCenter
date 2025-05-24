package org.example.currencycenter.dto;

import jakarta.validation.constraints.NotNull;

public record RequestChangePassword(@NotNull(message="old_password field can not be empty.") String old_password,@NotNull(message="new_password field can not be empty.") String new_password) {
}
