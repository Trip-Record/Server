package com.triprecord.triprecord.schedule.dto.response;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import com.triprecord.triprecord.user.dto.response.UserProfile;
import com.triprecord.triprecord.user.entity.TripStyle;
import com.triprecord.triprecord.user.entity.User;

import java.util.List;

public record ScheduleGetResponse(
        UserProfile userProfile,
        Long scheduleId,
        String scheduleTitle,
        List<SchedulePlaceGetResponse> schedulePlaces,
        String scheduleStartDate,
        String scheduleEndDate,
        List<ScheduleDetailGetResponse> scheduleDetails,
        Long scheduleLikeCount,
        Long scheduleCommentCount
) {
    public static ScheduleGetResponse of(User user,
                                         TripStyle userTripStyle,
                                         Schedule schedule,
                                         List<SchedulePlace> schedulePlaces,
                                         List<ScheduleDetail> scheduleDetails,
                                         Long scheduleLikeCount,
                                         Long scheduleCommentCount) {
        return new ScheduleGetResponse(
                UserProfile.of(user),
                schedule.getScheduleId(),
                schedule.getScheduleTitle(),
                schedulePlaces.stream()
                        .map(SchedulePlaceGetResponse::of)
                        .toList(),
                schedule.getScheduleStartDate().toString(),
                schedule.getScheduleEndDate().toString(),
                scheduleDetails.stream()
                        .map(ScheduleDetailGetResponse::of)
                        .toList(),
                scheduleLikeCount,
                scheduleCommentCount
        );
    }
}
