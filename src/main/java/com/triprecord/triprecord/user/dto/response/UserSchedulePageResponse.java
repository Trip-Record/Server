package com.triprecord.triprecord.user.dto.response;

import com.triprecord.triprecord.schedule.dto.response.ScheduleInfo;

import java.util.List;
import lombok.Builder;

@Builder
public record UserSchedulePageResponse(
        int totalPages,
        int pageNumber,
        List<ScheduleInfo> scheduleInfoList


) {
}
