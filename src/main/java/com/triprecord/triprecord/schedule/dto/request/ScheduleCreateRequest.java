package com.triprecord.triprecord.schedule.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record ScheduleCreateRequest(
        @NotBlank String scheduleTitle,
        @NotEmpty @Size(min = 1, max = 3) List<Long> schedulePlaceIds,
        @NotNull LocalDate scheduleStartDate,
        @NotNull LocalDate scheduleEndDate,
        @Valid @NotNull List<ScheduleDetailCreateRequest> scheduleDetails
) {
}