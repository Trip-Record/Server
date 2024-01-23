package com.triprecord.triprecord.schedule.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ScheduleUpdateRequest(
        String scheduleTitle,
        List<Long> placeIds,
        @NotNull LocalDate scheduleStartDate,
        @NotNull LocalDate scheduleEndDate,
        List<ScheduleDetailUpdateRequest> scheduleDetails
) {
}