package com.triprecord.triprecord.user;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequest(
        @NotNull String userEmail,
        @NotNull String userPassword
) {
}
