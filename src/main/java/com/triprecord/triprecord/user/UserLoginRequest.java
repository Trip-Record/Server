package com.triprecord.triprecord.user;

public record UserLoginRequest(
        String userEmail,
        String userPassword
) {
}
