package com.triprecord.triprecord.user.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserCreateRequest(
        @Email @NotNull String userEmail,
        @NotNull String userPassword,
        @NotNull String userNickname,
        @NotNull LocalDate userAge,
        @NotNull Long userBasicProfileId
) {
}