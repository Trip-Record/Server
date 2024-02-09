package com.triprecord.triprecord.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleCommentContentRequest(
        @NotBlank String content
) {
}
