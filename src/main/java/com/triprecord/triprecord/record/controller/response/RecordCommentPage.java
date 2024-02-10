package com.triprecord.triprecord.record.controller.response;

import com.triprecord.triprecord.record.dto.RecordCommentData;
import java.util.List;
import lombok.Builder;

@Builder
public record RecordCommentPage(
        int totalPages,
        int pageNumber,
        List<RecordCommentData> recordComments
) {
}
