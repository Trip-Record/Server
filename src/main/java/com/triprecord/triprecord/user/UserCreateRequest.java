package com.triprecord.triprecord.user;

import java.time.LocalDate;

public record UserCreateRequest(
        String userEmail,
        String userPassword,
        String userNickname,
        LocalDate userAge,
        Long userBasicProfileId,
        Long userTripStyleId
) {
}