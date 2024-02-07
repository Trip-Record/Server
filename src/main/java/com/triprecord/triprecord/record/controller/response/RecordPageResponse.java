package com.triprecord.triprecord.record.controller.response;

import java.util.List;
import lombok.Builder;

@Builder
public record RecordPageResponse(
        int totalPages,
        int pageNumber,
        List<RecordResponse> recordList
) {
}
