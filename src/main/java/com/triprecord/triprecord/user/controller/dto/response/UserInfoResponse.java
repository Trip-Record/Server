package com.triprecord.triprecord.user.controller.dto.response;

import com.triprecord.triprecord.user.entity.User;
import jakarta.validation.constraints.NotNull;

public record UserInfoResponse(
        @NotNull String userNickname,
        @NotNull String userProfileImg,
        String tripStyle,
        Long recordTotal,
        Long scheduleTotal,
        Long placeTotal,
        Long likeTotal
) {
    public static UserInfoResponse of(User user, Long recordTotal, Long scheduleTotal, Long placeTotal, Long likeTotal){
        return new UserInfoResponse(
                user.getUserNickname(),
                user.getUserProfileImg(),
                user.getUserTripStyle().getTripStyleName(),
                recordTotal,
                scheduleTotal,
                placeTotal,
                likeTotal
        );
    }

}