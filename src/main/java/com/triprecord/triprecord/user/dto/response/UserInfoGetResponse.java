package com.triprecord.triprecord.user.dto.response;

import com.triprecord.triprecord.user.entity.User;

public record UserInfoGetResponse(
        UserProfile userProfile,
        Long recordTotal,
        Long scheduleTotal,
        Long placeTotal,
        Long likeTotal
) {
    public static UserInfoGetResponse of(User user, Long recordTotal, Long scheduleTotal, Long placeTotal, Long likeTotal){
        return new UserInfoGetResponse(
                UserProfile.of(user),
                recordTotal,
                scheduleTotal,
                placeTotal,
                likeTotal
        );
    }

}