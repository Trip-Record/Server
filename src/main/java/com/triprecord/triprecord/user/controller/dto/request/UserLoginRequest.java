package com.triprecord.triprecord.user.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequest(
        @NotNull String userEmail,
        @NotNull String userPassword
) {
}
