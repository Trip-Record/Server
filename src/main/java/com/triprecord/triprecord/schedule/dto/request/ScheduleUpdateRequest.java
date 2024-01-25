package com.triprecord.triprecord.schedule.dto.request;

import java.time.LocalDate;
import java.util.List;

public record ScheduleUpdateRequest(
        String scheduleTitle,
        List<Long> placeIds,
        LocalDate scheduleStartDate,
        LocalDate scheduleEndDate,
        List<ScheduleDetailUpdateRequest> scheduleDetails
) {
}