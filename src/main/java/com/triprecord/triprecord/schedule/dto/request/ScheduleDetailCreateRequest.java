package com.triprecord.triprecord.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ScheduleDetailCreateRequest(
        @NotNull LocalDate scheduleDetailDate,
        @NotBlank String scheduleDetailContent
) {
}