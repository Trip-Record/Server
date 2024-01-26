package com.triprecord.triprecord.user.dto.response;

public record UserProfile(
        String userNickname,
        String userProfileImg,
        String userTripStyleName,
        String userTripStyleImg
) {
}