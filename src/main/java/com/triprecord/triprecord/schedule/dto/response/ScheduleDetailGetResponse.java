package com.triprecord.triprecord.schedule.dto.response;

import com.triprecord.triprecord.schedule.entity.ScheduleDetail;

public record ScheduleDetailGetResponse(
        String scheduleDetailDate,
        String scheduleContent
) {
    public static ScheduleDetailGetResponse of(ScheduleDetail scheduleDetail) {
        return new ScheduleDetailGetResponse(
                scheduleDetail.getScheduleDetailDate().toString(),
                scheduleDetail.getScheduleContent()
        );
    }
}
