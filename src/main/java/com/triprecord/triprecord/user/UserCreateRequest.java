package com.triprecord.triprecord.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
public record UserCreateRequest(
        @Email String userEmail,
        @NotNull String userPassword,
        @NotNull String userNickname,
        @NotNull LocalDate userAge,
        @NotNull Long userBasicProfileId,
        @NotNull Long userTripStyleId
) {
}