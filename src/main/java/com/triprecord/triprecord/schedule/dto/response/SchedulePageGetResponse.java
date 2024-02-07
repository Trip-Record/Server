package com.triprecord.triprecord.schedule.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record SchedulePageGetResponse(
        int totalPages,
        int pageNumber,
        List<ScheduleGetResponse> schedules
) {
}