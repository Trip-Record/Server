package com.triprecord.triprecord.user.dto.response;

import com.triprecord.triprecord.user.entity.User;

public record UserProfile(
        String userNickname,
        String userProfileImg,
        String userTripStyleName,
        String userTripStyleImg
) {
    public static UserProfile of(User user) {
        return new UserProfile(
                user.getUserNickname(),
                user.getUserProfileImg(),
                user.getUserTripStyle().getTripStyleName(),
                user.getUserTripStyle().getTripStyleImg()
        );
    }
}