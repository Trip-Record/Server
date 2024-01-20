package com.triprecord.triprecord.user;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserInfoRequest(
        @NotNull String userEmail,
        @NotNull String userNick,
        @NotNull LocalDate userAge,
        @NotNull String userProfileImg
        ) {
}
