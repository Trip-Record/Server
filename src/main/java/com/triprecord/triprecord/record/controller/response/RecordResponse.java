package com.triprecord.triprecord.record.controller.response;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.user.dto.response.UserProfile;
import com.triprecord.triprecord.user.entity.User;
import java.time.LocalDate;

public record RecordResponse(
        UserProfile recordUserProfile,
        Long recordId,
        String recordTitle,
        String recordContent,
        LocalDate tripStartDate,
        LocalDate tripEndDate,
        Long likeCount,
        Long commentCount

) {
    public static RecordResponse fromRecord(Record record, Long likeCount, Long commentCount){
        return new RecordResponse(
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
