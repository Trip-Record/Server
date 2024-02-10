package com.triprecord.triprecord.user.dto.response;

import com.triprecord.triprecord.record.dto.RecordInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRecordPageResponse(
        int totalPages,
        int pageNumber,
        List<RecordInfo> recordInfoList
) {
}
