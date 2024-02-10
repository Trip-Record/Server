package com.triprecord.triprecord.schedule.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ScheduleCommentPageGetResponse(
        int totalPages,
        int pageNumber,
        List<ScheduleCommentGetResponse> scheduleComments
) {
}
