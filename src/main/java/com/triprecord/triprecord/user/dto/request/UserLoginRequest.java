package com.triprecord.triprecord.user.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequest(
        @NotNull String userEmail,
        @NotNull String userPassword
) {
}
