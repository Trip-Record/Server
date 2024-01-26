package com.triprecord.triprecord.record.controller.response;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.user.dto.response.UserProfile;
import java.time.LocalDate;

public record RecordDataResponse(
        UserProfile recordUserProfile,
        Long recordId,
        String recordTitle,
        String recordContent,
        LocalDate tripStartDate,
        LocalDate tripEndDate,
        Long likeCount,
        Long commentCount

) {
    public static RecordDataResponse fromRecord(Record record, Long likeCount, Long commentCount){
        return new RecordDataResponse(
                UserProfile.of(record.getCreatedUser()),
                record.getRecordId(),
                record.getRecordTitle(),
                record.getRecordContent(),
                record.getTripStartDate(),
                record.getTripEndDate(),
                likeCount,
                commentCount
        );
    }
}
