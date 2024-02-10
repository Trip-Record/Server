package com.triprecord.triprecord.schedule.dto.response;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import java.util.List;

public record ScheduleInfo(
        Long scheduleId,
        String scheduleTitle,
        List<SchedulePlaceGetResponse> schedulePlaces,
        String scheduleStartDate,
        String scheduleEndDate,
        List<ScheduleDetailGetResponse> scheduleDetails,
        Long scheduleLikeCount,
        Long scheduleCommentCount) {
    public static ScheduleInfo of(Schedule schedule,
                                         List<SchedulePlace> schedulePlaces,
                                         List<ScheduleDetail> scheduleDetails,
                                         Long scheduleLikeCount,
                                         Long scheduleCommentCount){
        return new ScheduleInfo(
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
