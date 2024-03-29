package com.triprecord.triprecord.record.controller.response;

import com.triprecord.triprecord.location.dto.PlaceBasicData;
import com.triprecord.triprecord.record.dto.RecordImageData;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.user.dto.response.UserProfile;
import java.time.LocalDate;
import java.util.List;

public record RecordResponse(
        UserProfile recordUserProfile,
        List<PlaceBasicData> recordPlaces,
        Long recordId,
        String recordTitle,
        String recordContent,
        LocalDate tripStartDate,
        LocalDate tripEndDate,
        List<RecordImageData> recordImages,
        boolean isUserCreated,
        boolean isUserLiked,
        Long likeCount,
        Long commentCount

) {
    public static RecordResponse fromRecordData(Record record,
                                                List<PlaceBasicData> recordPlaces,
                                                List<RecordImageData> images,
                                                boolean isUserCreated,
                                                boolean isUserLiked,
                                                Long likeCount,
                                                Long commentCount){
        return new RecordResponse(
                UserProfile.of(record.getCreatedUser()),
                recordPlaces,
                record.getRecordId(),
                record.getRecordTitle(),
                record.getRecordContent(),
                record.getTripStartDate(),
                record.getTripEndDate(),
                images,
                isUserCreated,
                isUserLiked,
                likeCount,
                commentCount
        );
    }
}
