package com.triprecord.triprecord.schedule.dto.request;

import java.time.LocalDate;

public record ScheduleDetailUpdateRequest(
        LocalDate scheduleDetailDate,
        String scheduleDetailContent
) {
}